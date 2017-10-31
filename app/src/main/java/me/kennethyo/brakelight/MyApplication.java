package me.kennethyo.brakelight;

import android.app.Application;

import me.kennethyo.library.brakelight.BrakeLight;
import me.kennethyo.library.brakelight.internal.BrakeLightWatch;

/**
 * Created by kenneth on 2016/10/17.
 */

public class MyApplication extends Application {

  private static MyApplication instance;

  public static MyApplication getInstance() {
    return instance;
  }

  private BrakeLightWatch watch;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    watch = BrakeLight.install(this);
  }

  public BrakeLightWatch getWatch() {
    return watch;
  }
}
