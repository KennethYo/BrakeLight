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

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kenneth on 2016/10/17.
 */

public final class BrakeLightWatch implements Thread.UncaughtExceptionHandler {
  private final Context context;
  private Thread.UncaughtExceptionHandler exceptionHandler;

  public BrakeLightWatch(Context context) {
    this.context = context.getApplicationContext();
    exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  @Override public void uncaughtException(Thread thread, Throwable throwable) {
    watch(throwable);
    exceptionHandler.uncaughtException(thread, throwable);
  }

  public BrakeLightWatch watch(Throwable throwable) {
    return watch(Log.getStackTraceString(throwable));
  }

  public BrakeLightWatch watch(String msg) {
    saveLight(msg);
    return this;
  }

  private void saveLight(final String msg) {
    BrakeLightInternal.executeOnFileIoThread(new Runnable() {
      @Override public void run() {
        if (!BrakeLightInternal.hasSDCard()) return;
        writeFile(msg);
      }
    });
  }

  private void writeFile(String msg) {
    String fileName =
        new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS'.watch'", Locale.CHINA).format(new Date());

    File path = BrakeLightInternal.externalStorageDirectory(context);
    if (!BrakeLightInternal.directoryWritableAfterMkdirs(path)) return;

    File file = new File(path, fileName);

    try {
      msg = msg.replaceAll("\n", "\n\n");

      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(msg);
      fileWriter.close();

      DisplayLightService.sendResultToListener(context, new Light(msg, fileName, file.getPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
