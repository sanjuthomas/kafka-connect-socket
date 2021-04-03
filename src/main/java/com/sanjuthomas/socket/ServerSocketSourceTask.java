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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

/**
 * @author Sanju Thomas
 */
@Slf4j
public class ServerSocketSourceTask extends SourceTask {

  private final BlockingQueue<String> queue = new ArrayBlockingQueue(1048576);
  private ServerSocketConnectorConfig config;

  @Override
  public String version() {
    return "1.0.0";
  }

  @Override
  public void start(Map<String, String> config) {
    this.config = new ServerSocketConnectorConfig(config);
    log.info("ServerSocketSourceTask starting with configs {}", config);
    new TCPServer(this.config, queue).start();
    log.info("ServerSocketSourceTask started", config);
  }

  @Override
  public List<SourceRecord> poll() {
    log.info("ServerSocketSourceTask poll called");
    final List<SourceRecord> records = new ArrayList<>();
    final String message = queue.poll();
    if(null != message) records.add(new SourceRecord(null, null, config.topic(), 0, Schema.STRING_SCHEMA, message));
    if(records.isEmpty()) return null;
    return records;
  }

  @Override
  public void stop() {
    log.info("ServerSocketSourceTask stopped");
  }
}