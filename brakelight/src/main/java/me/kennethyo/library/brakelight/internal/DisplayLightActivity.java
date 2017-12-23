/*
 * Copyright 2017 Kenneth Yo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.kennethyo.library.brakelight.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    if (light != null && !TextUtils.isEmpty(light.msg)) {
      String msg = light.msg.replaceAll("\n", "\n\n");
      SpannableStringBuilder sb = new SpannableStringBuilder(msg);
      Pattern pattern = Pattern.compile("([\\w]+Exception:)|(Caused by:)");
      Matcher matcher = pattern.matcher(sb);
      while (matcher.find()) {
        sb.setSpan(new ForegroundColorSpan(Color.RED), matcher.start(), matcher.end(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      tvMsg.setText(sb);
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
