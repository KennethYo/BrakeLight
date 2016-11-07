package me.kennethyo.library.ybrakelight.internal;

import java.io.Serializable;

/**
 * Created by kenneth on 2016/10/17.
 */

public class Light implements Serializable {
  public final String msg;
  public final String fileName;
  public final String filePath;

  public Light(String msg, String fileName, String filePath) {
    this.msg = msg;
    this.fileName = fileName;
    this.filePath = filePath;
  }
}
