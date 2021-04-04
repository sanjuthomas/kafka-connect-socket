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

/**
 * @author Sanju Thomas
 */
package com.sanjuthomas.socket.client;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class TcpClient {

  private final ClientSocketConnectorConfig config;
  private final BlockingQueue<String> queue;

  public void start() {
    new Thread(() -> {
      reactor.netty.tcp.TcpClient.create()
        .host(config.host())
        .port(config.port())
        .handle((inbound, outbound) -> inbound.receive()
          .asString()
          .flatMap(message -> {
            try {
              log.trace("message arrived - {}", message);
              queue.put(message);
            } catch (InterruptedException e) {
              log.error(e.getMessage(), e);
            }
            return Mono.empty();
          }))
        .connectNow();
    }).start();
  }
}
