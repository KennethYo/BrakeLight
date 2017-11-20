/*
 * Copyright 2017 Kenneth Yo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.kennethyo.library.brakelight.internal;

import android.util.Log;

final class BrakeLightLog {

  private static volatile Logger logger = new DefaultLogger();

  interface Logger {
    void d(String message, Object... args);

    void d(Throwable throwable, String message, Object... args);
  }

  private static class DefaultLogger implements Logger {
    DefaultLogger() { }

    @Override public void d(String message, Object... args) {
      String formatted = String.format(message, args);
      if (formatted.length() < 4000) {
        Log.d("BrakeLight", formatted);
      } else {
        String[] lines = formatted.split("\n");
        for (String line : lines) {
          Log.d("BrakeLight", line);
        }
      }
    }

    @Override public void d(Throwable throwable, String message, Object... args) {
      d(String.format(message, args) + '\n' + Log.getStackTraceString(throwable));
    }
  }

  public static void setLogger(Logger logger) {
    BrakeLightLog.logger = logger;
  }

  static void d(String message, Object... args) {
    // Local variable to prevent the ref from becoming null after the null check.
    Logger logger = BrakeLightLog.logger;
    if (logger == null) {
      return;
    }
    logger.d(message, args);
  }

  static void d(Throwable throwable, String message, Object... args) {
    // Local variable to prevent the ref from becoming null after the null check.
    Logger logger = BrakeLightLog.logger;
    if (logger == null) {
      return;
    }
    logger.d(throwable, message, args);
  }

  private BrakeLightLog() {
    throw new AssertionError();
  }
}
