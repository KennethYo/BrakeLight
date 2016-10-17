package com.yiche.library.brakelight.internal;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.yiche.library.brakelight.internal.BrakeLightInternal.directoryWritableAfterMkdirs;
import static com.yiche.library.brakelight.internal.BrakeLightInternal.executeOnFileIoThread;
import static com.yiche.library.brakelight.internal.BrakeLightInternal.externalStorageDirectory;
import static com.yiche.library.brakelight.internal.BrakeLightInternal.hasSDCard;

/**
 * Created by kenneth on 2016/10/17.
 */

public final class BrakeLightWatch {
  private final Context context;

  public BrakeLightWatch(Context context) {
    this.context = context.getApplicationContext();
  }

  //public BrakeLightWatch listenerServiceClass(Class listenerServiceClass) {
  //  setEnabled(context, listenerServiceClass, true);
  //  return this;
  //}

  public BrakeLightWatch watch(Throwable throwable) {

    return watch(Log.getStackTraceString(throwable));
  }

  public BrakeLightWatch watch(String msg) {
    saveLight(msg);
    return this;
  }

  private void saveLight(final String msg) {
    executeOnFileIoThread(new Runnable() {
      @Override public void run() {
        if (!hasSDCard()) return;
        writeFile(msg);
      }
    });
  }

  private void writeFile(String msg) {
    String fileName =
        new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS'.watch'", Locale.CHINA).format(new Date());

    File path = externalStorageDirectory(context);
    if (!directoryWritableAfterMkdirs(path)) return;

    File file = new File(path, fileName);

    try {
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(msg);
      fileWriter.close();

      DisplayLightService.sendResultToListener(context, new Light(msg, fileName, file.getPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
