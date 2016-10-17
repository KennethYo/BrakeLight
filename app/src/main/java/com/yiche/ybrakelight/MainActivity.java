package com.yiche.ybrakelight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.text);
    textView.setText(do_exec("dig www.baidu.com"));
  }

  String do_exec(String cmd) {
    String s = "\n";
    try {
      Process p = Runtime.getRuntime().exec(cmd);
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = null;
      while ((line = in.readLine()) != null) {
        s += line;
      }
    } catch (IOException e) {
      e.printStackTrace();
      MyApplication.getInstance().getWatch().watch(e);
    }
    return s;
  }
}
