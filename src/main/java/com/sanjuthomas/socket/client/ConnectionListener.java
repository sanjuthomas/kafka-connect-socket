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
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;

/**
 * @author Sanju Thomas
 */
@RequiredArgsConstructor
public class ConnectionListener implements ChannelFutureListener {

  private final TcpClient client;
  private final ClientSocketConnectorConfig config;
  private final EventLoopGroup loop;

  @Override
  public void operationComplete(ChannelFuture channelFuture) {
    if (!channelFuture.isSuccess()) {
      loop.schedule(new Runnable() {
        @Override
        public void run() {
          client.createBootstrap(new Bootstrap(), loop);
        }
      }, config.reconnectDelay(), TimeUnit.SECONDS);
    }
  }
}