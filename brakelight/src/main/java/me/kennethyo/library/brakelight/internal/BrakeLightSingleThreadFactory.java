package me.kennethyo.library.brakelight.internal;

import java.util.concurrent.ThreadFactory;

/**
 * Created by kenneth on 2016/10/17.
 */
final class BrakeLightSingleThreadFactory implements ThreadFactory {

  private final String threadName;

  BrakeLightSingleThreadFactory(String threadName) {
    this.threadName = "BrakeLight-" + threadName;
  }

  @Override public Thread newThread(Runnable r) {
    return new Thread(r, threadName);
  }
}
