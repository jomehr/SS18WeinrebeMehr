package com.example.jan.kassenzettel_scan.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.activities.MainActivity;
import com.example.jan.kassenzettel_scan.fragments.ReceiptList_Group;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Payload: " + remoteMessage.getData());

/*            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/
            /*String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            sendNotification(title, body);*/
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: Titlt: " + remoteMessage.getNotification().getTitle() + " Body: " + remoteMessage.getNotification().getBody());

            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            sendNotification(title, body);
        }
    }

    private void sendNotification(String title, String messageBody) {
        Log.d(TAG, "Building Notification...");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (getApplicationContext(), 0  /*Request code*/ , intent, PendingIntent.FLAG_ONE_SHOT);

        if (title == null) title="TEST123";
        if (messageBody == null) messageBody ="TESTBESCHREIBUNG";

        String channelId = "Test";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        // Since android Oreo notification channel is needed.
        Log.d(TAG, String.valueOf(Build.VERSION.SDK_INT) + " " + String.valueOf(Build.VERSION_CODES.O));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Setting up notification channel");
            NotificationChannel channel = new NotificationChannel(channelId,
                    title,
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
            Log.d(TAG, "TESTING");
            notificationManager.notify(1  /*ID of notification */, notificationBuilder.build());
        }
    }
}
