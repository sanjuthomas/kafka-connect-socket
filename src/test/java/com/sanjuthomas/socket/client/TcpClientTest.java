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

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Sanju Thomas
 */
class TcpClientTest {

  private final BlockingQueue<String> queue = new ArrayBlockingQueue(1048576);
  private TcpClient tcpClient;

  @BeforeEach
  void setUp() {
    tcpClient = new TcpClient(new ClientSocketConnectorConfig(Map.of("their.tcp.host", "localhost", "their.tcp.port", "12001", "reconnect.delay.seconds", "60", "topic", "test")), queue);
  }

  @Test
  void shouldStartClient() throws InterruptedException {
    tcpClient.start();
    Thread.sleep(1000000000);
  }

}
