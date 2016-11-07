package me.kennethyo.brakelight;

import android.app.Application;
import me.kennethyo.library.ybrakelight.YBrakeLight;
import me.kennethyo.library.ybrakelight.internal.BrakeLightWatch;

/**
 * Created by kenneth on 2016/10/17.
 */

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

  private static MyApplication instance;
  private Thread.UncaughtExceptionHandler exceptionHandler;

  public static MyApplication getInstance() {
    return instance;
  }

  private BrakeLightWatch watch;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    watch = YBrakeLight.install(this);
    exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  public BrakeLightWatch getWatch() {
    return watch;
  }

  @Override public void uncaughtException(Thread t, Throwable e) {
    getWatch().watch(e);
    exceptionHandler.uncaughtException(t, e);
  }
}
