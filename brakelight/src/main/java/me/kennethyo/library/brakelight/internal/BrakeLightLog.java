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
