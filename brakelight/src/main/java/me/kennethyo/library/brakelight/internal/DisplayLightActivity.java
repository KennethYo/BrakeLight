package me.kennethyo.library.brakelight.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import me.kennethyo.library.brakelight.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class DisplayLightActivity extends Activity implements View.OnClickListener {
  private static final String SHOW_WATCH_EXTRA = "show_latest";
  private TextView tvMsg;
  private Button btnShare;
  private Light light;

  public static PendingIntent createPendingIntent(Context context, Light light) {
    Intent intent = new Intent(context, DisplayLightActivity.class);
    intent.putExtra(SHOW_WATCH_EXTRA, light);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    return PendingIntent.getActivity(context, 1, intent, FLAG_UPDATE_CURRENT);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(me.kennethyo.library.brakelight.R.layout.activity_display_watch);

    tvMsg = (TextView) findViewById(me.kennethyo.library.brakelight.R.id.tv_msg);
    btnShare = (Button) findViewById(R.id.btn_share);
    btnShare.setOnClickListener(this);

    light = (Light) getIntent().getSerializableExtra(SHOW_WATCH_EXTRA);
    if (light != null) {
      tvMsg.setText(light.msg);
    }
  }

  @Override public void onClick(View view) {
    if (view == btnShare && null != light) {
      // 文本分享
      Intent share_intent = new Intent();
      share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
      share_intent.setType("text/plain");//设置分享内容的类型
      share_intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));//添加分享内容标题
      share_intent.putExtra(Intent.EXTRA_TEXT, light.msg);//添加分享内容
      //创建分享的Dialog
      share_intent = Intent.createChooser(share_intent, getString(R.string.crash_info));
      startActivity(share_intent);
    }
  }
}