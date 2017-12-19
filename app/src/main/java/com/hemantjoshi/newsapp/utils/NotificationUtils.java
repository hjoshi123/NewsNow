package com.hemantjoshi.newsapp.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.hemantjoshi.newsapp.NewsDetailsActivity;
import com.hemantjoshi.newsapp.R;
import com.hemantjoshi.newsapp.newsmain.MainActivity;

/**
 * @author HemantJ.
 * Notification Class that displays notification
 */

public class NotificationUtils {
    private static final int TOI_REMINDER_PENDING_INTENT_ID = 123;
    private static final String TOI_REMINDER_NOTIFICATION_CHANNEL = "timesofindia";
    private static final int TOI_REMINDER_ID = 1234;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserAboutNews(Context context, String title, String urlToText){

        NotificationManager notificationManager = (NotificationManager)
                                        context.getSystemService(Context.NOTIFICATION_SERVICE);
        /*
            Notification Channel for devices using more than Android O
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    TOI_REMINDER_NOTIFICATION_CHANNEL,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, TOI_REMINDER_NOTIFICATION_CHANNEL)
                /*
                    sets color of the notification
                 */
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                /*
                    sets small icon to show in the status/system action bar
                 */
                .setSmallIcon(R.mipmap.ic_launcher)
                /*
                    sets large icon to show when the drop down menu is dragged
                 */
                .setLargeIcon(largeIcon(context))
                /*
                    setting the title of the notification
                 */
                .setContentTitle("News Now")
                /*
                    setting the text/content of the notification
                 */
                .setContentText(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                /*
                    phone to vibrate when notification arrives
                 */
                .setDefaults(Notification.DEFAULT_VIBRATE)
                /*
                    PendingIntent to open the app when clicked on notification
                 */
                .setContentIntent(contentIntent(context, urlToText))
                .setAutoCancel(true);

        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(TOI_REMINDER_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context, String urlToText) {
        Intent startActivityIntent = new Intent(context, NewsDetailsActivity.class);
        startActivityIntent.putExtra("link",urlToText);
        return PendingIntent.getActivity(
                context,
                TOI_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        return largeIcon;
    }
}
