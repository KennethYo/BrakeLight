package com.yiche.ybrakelight;

import android.app.Application;
import com.yiche.library.brakelight.BrakeLight;
import com.yiche.library.brakelight.internal.BrakeLightWatch;

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
