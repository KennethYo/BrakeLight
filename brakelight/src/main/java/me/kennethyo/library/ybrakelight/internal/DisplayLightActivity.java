package me.kennethyo.library.ybrakelight.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class DisplayLightActivity extends Activity {
  private static final String SHOW_WATCH_EXTRA = "show_latest";
  private TextView tvMsg;

  public static PendingIntent createPendingIntent(Context context, Light light) {
    Intent intent = new Intent(context, DisplayLightActivity.class);
    intent.putExtra(SHOW_WATCH_EXTRA, light);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    return PendingIntent.getActivity(context, 1, intent, FLAG_UPDATE_CURRENT);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(me.kennethyo.library.ybrakelight.R.layout.activity_display_watch);

    tvMsg = (TextView) findViewById(me.kennethyo.library.ybrakelight.R.id.tv_msg);

    Light light = (Light) getIntent().getSerializableExtra(SHOW_WATCH_EXTRA);
    if (light != null) {
      tvMsg.setText(light.msg);
    }
  }
}
