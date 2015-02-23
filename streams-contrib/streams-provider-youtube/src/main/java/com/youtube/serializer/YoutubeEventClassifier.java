/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.youtube.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.services.youtube.model.Video;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.streams.jackson.StreamsJacksonMapper;

import java.io.IOException;

public class YoutubeEventClassifier {
    private static ObjectMapper mapper = StreamsJacksonMapper.getInstance();
    private static final String VIDEO_IDENTIFIER = "\"youtube#video\"";

    public static Class detectClass(String json) {
        Preconditions.checkNotNull(json);
        Preconditions.checkArgument(StringUtils.isNotEmpty(json));

        ObjectNode objectNode;
        try {
            objectNode = (ObjectNode) mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (objectNode.findValue("kind") != null && objectNode.get("kind").toString().equals(VIDEO_IDENTIFIER)) {
            return Video.class;
        }  else  {
            return ObjectNode.class;
        }
    }
}
