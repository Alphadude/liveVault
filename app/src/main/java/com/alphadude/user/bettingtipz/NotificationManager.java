package com.alphadude.user.bettingtipz;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class NotificationManager {

    private Context ctx;
    public static final int NOTIFICATIONID = 234;

    public NotificationManager(Context ctx) {
        this.ctx = ctx;
    }

    public void shownotifiaction(String from,String notification,Intent intent){

        PendingIntent pendingIntent = PendingIntent.getActivity(
                ctx,
                NOTIFICATIONID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        Notification mNotification = builder.setSmallIcon(R.mipmap.icon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),R.mipmap.icon))
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        android.app.NotificationManager manager = (android.app.NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATIONID,mNotification);
    }
}
