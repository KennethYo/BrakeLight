package com.yiche.ybrakelight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.text);
    textView.setText(do_exec("ping -R www.baidu.com"));
  }

  String do_exec(String cmd) {
    String s = "\n";
    Process p = null;
    BufferedReader in = null;
    InputStream inputStream = null;
    InputStreamReader inputStreamReader = null;
    try {
      p = Runtime.getRuntime().exec(cmd);
      inputStream = p.getInputStream();
      inputStreamReader = new InputStreamReader(inputStream);
      in = new BufferedReader(inputStreamReader);
      String line = null;
      while ((line = in.readLine()) != null) {
        s += line;
      }
    } catch (IOException e) {
      e.printStackTrace();
      MyApplication.getInstance().getWatch().watch(e);
    } finally {
      if (p != null) {
        p.destroy();
      }
      close(in);
      close(inputStream);
      close(inputStreamReader);
    }
    return s;
  }

  private void close(Closeable close) {
    if (close == null) return;
    try {
      close.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
