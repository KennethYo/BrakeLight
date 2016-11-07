package me.kennethyo.brakelight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn) public void testCrash() {
    throw new NullPointerException("test crash");
  }

  @OnClick(R.id.btn1) public void testCatch() {
    String a = null;
    try {
      char[] chars = a.toCharArray();
    } catch (Exception e) {
      e.printStackTrace();
      MyApplication.getInstance().getWatch().watch(e);
    }
  }
}
