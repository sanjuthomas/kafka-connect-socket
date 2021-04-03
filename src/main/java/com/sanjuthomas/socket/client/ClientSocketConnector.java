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

import com.sanjuthomas.socket.server.ServerSocketConnectorConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;


/**
 * @author Sanju Thomas
 */
@Slf4j
public class ClientSocketConnector extends SourceConnector {

  private Map<String, String> config;

  @Override
  public String version() {
    return "1.0.0";
  }

  @Override
  public void start(Map<String, String> map) {
    log.info("Starting ClientSocketConnector.....");
    config = new HashMap<>(map);
  }

  @Override
  public Class<? extends Task> taskClass() {
    return ClientSocketSourceTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int taskCount) {
    return IntStream.range(0, taskCount)
      .mapToObj(i -> new HashMap<>(config))
      .collect(Collectors.toList());
  }

  @Override
  public void stop() {

  }

  @Override
  public ConfigDef config() {
    return ServerSocketConnectorConfig.conf();
  }
}
