/*
 * Copyright (c) 2020 Sanju Thomas
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Sanju Thomas
 */
public class SocketListenerClient {

  public static void main(String[] args) {

    String hostname = "localhost";
    int port = 12000;

    try (Socket socket = new Socket(hostname, port)) {

      InputStream input = socket.getInputStream();
      InputStreamReader reader = new InputStreamReader(input);

      int character;
      StringBuilder data = new StringBuilder();

      while ((character = reader.read()) != -1) {
        data.append((char) character);
      }

      System.out.println(data);


    } catch (UnknownHostException ex) {

      System.out.println("Server not found: " + ex.getMessage());

    } catch (IOException ex) {

      System.out.println("I/O error: " + ex.getMessage());
    }



   /* Connection connection =
      TcpClient.create()
        .host("localhost")
        .port(12000)
        .handle((inbound, outbound) -> inbound.receive().asString().flatMap(message -> {
          System.out.println(message);
          return Mono.empty();
        }))
        .connectNow();
    connection.onDispose().block();*/
  }

}
