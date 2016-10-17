package com.yiche.library.brakelight;

import android.app.Application;
import com.yiche.library.brakelight.internal.BrakeLightWatch;

/**
 * Created by kenneth on 2016/10/17.
 */

public class BrakeLight {

  public static BrakeLightWatch install(Application application) {
    return new BrakeLightWatch(application);
  }

  private BrakeLight() {
    throw new AssertionError();
  }
}
