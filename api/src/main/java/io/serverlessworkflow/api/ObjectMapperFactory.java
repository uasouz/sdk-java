/*
 * Copyright 2020-Present The Serverless Workflow Specification Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.serverlessworkflow.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import io.serverlessworkflow.api.types.CallTask;
import io.serverlessworkflow.api.types.Switch;
import io.serverlessworkflow.api.types.Task;
import io.serverlessworkflow.api.types.TaskItem;

class ObjectMapperFactory {

  private static final ObjectMapper jsonMapper = configure(new ObjectMapper());

  private static final ObjectMapper yamlMapper =
      configure(new ObjectMapper(new YAMLFactory().enable(Feature.MINIMIZE_QUOTES)));

  public static final ObjectMapper jsonMapper() {
    return jsonMapper;
  }

  public static final ObjectMapper yamlMapper() {
    return yamlMapper;
  }

  private static ObjectMapper configure(ObjectMapper mapper) {
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addDeserializer(Task.class, new TaskDeserializer());
    simpleModule.addSerializer(Task.class, new TaskSerializer());
    simpleModule.addDeserializer(CallTask.class, new CallTaskDeserializer());
    simpleModule.addSerializer(CallTask.class, new CallTaskSerializer());
    simpleModule.addDeserializer(TaskItem.class, new TaskItemDeserializer());
    simpleModule.addSerializer(TaskItem.class, new TaskItemSerializer());
    simpleModule.addSerializer(Switch.class, new SwitchSerializer());
    simpleModule.addDeserializer(Switch.class, new SwitchDeserializer());

    return mapper
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
        .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
        .registerModule(simpleModule);
  }

  private ObjectMapperFactory() {}
}
