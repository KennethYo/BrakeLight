package me.kennethyo.library.ybrakelight.internal;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

/**
 * Created by kenneth on 2016/10/17.
 */

public class DisplayLightService extends IntentService {
  private static final String WATCH_FILE = "watch_file";

  public static void sendResultToListener(Context context, Light light) {
    Intent intent = new Intent(context, DisplayLightService.class);
    intent.putExtra(WATCH_FILE, light);
    context.startService(intent);
  }

  public DisplayLightService() {
    super(DisplayLightService.class.getName());
  }

  @Override protected void onHandleIntent(Intent intent) {
    Light light = (Light) intent.getSerializableExtra(WATCH_FILE);
    if (light == null) {
      return;
    }
    onDisplay(light);
  }

  private void onDisplay(Light light) {
    String contentTitle = "YBrakeLight";
    String contentText = "Click for more details";

    PendingIntent pendingIntent = DisplayLightActivity.createPendingIntent(this, light);

    int notificationId = (int) (SystemClock.uptimeMillis() / 1000);
    showNotification(this, contentTitle, contentText, pendingIntent, notificationId);
  }

  private static void showNotification(Context context, CharSequence contentTitle,
      CharSequence contentText, PendingIntent pendingIntent, int notificationId) {
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    Notification notification;
    Notification.Builder builder = new Notification.Builder(context) //
        .setSmallIcon(me.kennethyo.library.ybrakelight.R.drawable.ic_brake_light)
        .setWhen(System.currentTimeMillis())
        .setContentTitle(contentTitle)
        .setContentText(contentText)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent);
    if (SDK_INT < JELLY_BEAN) {
      notification = builder.getNotification();
    } else {
      notification = builder.build();
    }
    notificationManager.notify(notificationId, notification);
  }
}
