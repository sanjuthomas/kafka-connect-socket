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

package com.sanjuthomas.socket.server;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

/**
 * @author Sanju Thomas
 */
public class ServerSocketConnectorConfig extends AbstractConfig {

  private static final String TCP_PORT = "my.tcp.port";
  private static final String TCP_PORT_DOC = "Where should I listen for an incoming connection?";

  private static final String TOPIC = "topic";
  private static final String TOPIC_DOC = "Topic to with the messages should be written?";

  public ServerSocketConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public ServerSocketConnectorConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
      .define(TCP_PORT, Type.INT, Importance.HIGH, TCP_PORT_DOC)
      .define(TOPIC, Type.STRING, Importance.HIGH, TOPIC_DOC);
  }

  public Integer myTcpPort() {
    return this.getInt(TCP_PORT);
  }

  public String topic() {
    return this.getString(TOPIC);
  }
}
