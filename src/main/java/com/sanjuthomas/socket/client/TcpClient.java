/*
 * Copyright (c) 2021 Sanju Thomas
 * Licensed under the MIT License (the "License");
 * You may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at https://en.wikipedia.org/wiki/MIT_License
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.sanjuthomas.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanju Thomas
 */
@RequiredArgsConstructor
@Slf4j
public class TcpClient {

  private final ClientSocketConnectorConfig config;
  private final BlockingQueue<String> queue;

  public Bootstrap start() {
    return createBootstrap(new Bootstrap(), new NioEventLoopGroup());
  }

  Bootstrap createBootstrap(final Bootstrap bootstrap, final EventLoopGroup eventLoop) {
    final InboundClientHandler handler = new InboundClientHandler(this, config, eventLoop);
    bootstrap.group(eventLoop);
    bootstrap.channel(NioSocketChannel.class);
    bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
    bootstrap.handler(new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast("InboundClientHandler", handler);
      }
    });
    bootstrap.remoteAddress(config.host(), config.port());
    bootstrap.connect().addListener(new ConnectionListener(this, config, eventLoop));
    return bootstrap;
  }

  @RequiredArgsConstructor
  class InboundClientHandler extends SimpleChannelInboundHandler<Object> {

    private final TcpClient tcpClient;
    private final ClientSocketConnectorConfig config;
    private final EventLoopGroup loop;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
      log.info("Connected to {} " + ctx.channel().remoteAddress());
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) {
      if (!(evt instanceof IdleStateEvent)) {
        return;
      }
      final IdleStateEvent e = (IdleStateEvent) evt;
      if (e.state() == IdleState.READER_IDLE) {
        log.error("Disconnecting due to no inbound traffic");
        ctx.close();
      }
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
      log.info("Disconnected from {}:{} ", config.host(), config.port());
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) {
      ctx.channel().eventLoop().schedule(() -> {
        log.info("Reconnecting to {}:{}", config.host(), config.port());
        tcpClient.start();
      }, config.reconnectDelay(), TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
      log.error(cause.getMessage() ,cause);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object payload)
      throws InterruptedException {
      final String message = ((ByteBuf) payload).toString(Charset.defaultCharset());
      queue.put(message);
    }
  }
}
