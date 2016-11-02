/*
 * Copyright 2004-present Facebook. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.facebook.watchman;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Called a "strategy" because we might have different ways of testing for capabilities as versions
 * change. Should become an interface once we get different implementations available.
 */
public class CapabilitiesStrategy {

  /**
   * Tests if a client supports the "watch-project" command or not.
   */
  public static boolean checkWatchProjectCapability(WatchmanClient client) {
    ListenableFuture<Map<String, Object>> future = client.version(
        Collections.<String>emptyList(),
        Collections.singletonList("cmd-watch-project"));
    try {
      Map<String, Object> response = future.get();
      return response.containsKey("capabilities");
    } catch (InterruptedException e) {
      return false;
    } catch (ExecutionException e) {
      return false;
    }
  }
}
