package me.kennethyo.library.ybrakelight.internal;

import android.content.Context;
import android.util.Log;

/**
 * Created by kenneth on 2016/10/17.
 */

public class BrakeLightWatch {

  public BrakeLightWatch(Context context) {
  }

  public BrakeLightWatch watch(Throwable throwable) {

    return watch(Log.getStackTraceString(throwable));
  }

  public BrakeLightWatch watch(String msg) {
    return this;
  }
}
