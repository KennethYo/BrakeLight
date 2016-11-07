package me.kennethyo.library.ybrakelight;

import android.app.Application;
import me.kennethyo.library.ybrakelight.internal.BrakeLightWatch;

/**
 * Created by kenneth on 2016/10/17.
 */

public class YBrakeLight {

  public static BrakeLightWatch install(Application application) {
    return new BrakeLightWatch(application);
  }

  private YBrakeLight() {
    throw new AssertionError();
  }
}
