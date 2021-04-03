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

package com.sanjuthomas.socket;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

@Slf4j
public class TcpClientTest {

  public static void main(String[] args) throws InterruptedException {

    new Thread(() -> {
      TcpClient.create()
        .host("localhost")
        .port(12001)
        .handle((inbound, outbound) -> inbound.receive()
          .asString()
          .flatMap(message -> {
            System.out.println(message);

            return Mono.empty();
          }))
        .connectNow();
    }).start();

     Thread.sleep(1000000);
  }
}
