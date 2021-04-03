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

import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

public class SocketSendClient {

  public static void main(String[] args) {
    Connection connection =
      TcpClient.create()
        .host("localhost")
        .port(12000)
        .handle((inbound, outbound) -> outbound.sendString(Mono.just("{\"id\" : \"ass\"}")))
        .connectNow();
    connection.onDispose()
      .block();
  }

}
