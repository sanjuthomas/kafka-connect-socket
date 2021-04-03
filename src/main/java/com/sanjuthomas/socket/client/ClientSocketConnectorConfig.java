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

package com.sanjuthomas.socket.client;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

/**
 * @author Sanju Thomas
 */
public class ClientSocketConnectorConfig extends AbstractConfig {

  private static final String TCP_HOST = "their.tcp.host";
  private static final String TCP_HOST_DOC = "Their host to connect";

  private static final String TCP_PORT = "their.tcp.port";
  private static final String TCP_PORT_DOC = "Their tcp port to connect";

  private static final String TOPIC = "topic";
  private static final String TOPIC_DOC = "Topic to with the messages should be written?";

  public ClientSocketConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public ClientSocketConnectorConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
      .define(TCP_HOST, Type.STRING, Importance.HIGH, TCP_HOST_DOC)
      .define(TCP_PORT, Type.INT, Importance.HIGH, TCP_PORT_DOC)
      .define(TOPIC, Type.STRING, Importance.HIGH, TOPIC_DOC);
  }

  public Integer theirTcpPort() {
    return this.getInt(TCP_PORT);
  }

  public String theirTcpHost() { return this.getString(TCP_HOST); }

  public String topic() {
    return this.getString(TOPIC);
  }
}
