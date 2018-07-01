package com.example.jan.kassenzettel_scan.network;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.activities.MainActivity;
import com.example.jan.kassenzettel_scan.activities.ReceiptDetails;
import com.example.jan.kassenzettel_scan.fragments.ReceiptList_Group;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMMessagingService";

    private static final int RECEIPTLISTGROUP = 1;
    private static final int RECEIPTLISTSOLO = 2;
    private static final int RECEIPTDETAIL = 3;
    private static final int SETTLEMENTDETAIL = 4;

    private Intent intent;
    private PendingIntent pendingIntent;
    private int requestCode = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //check if message contains key-value data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Payload: " + remoteMessage.getData());
            requestCode = Integer.parseInt(remoteMessage.getData().get("activity"));

            if (requestCode == RECEIPTLISTGROUP || requestCode ==RECEIPTLISTSOLO) intent = new Intent(this, MainActivity.class);
            if (requestCode == RECEIPTDETAIL) intent = new Intent(this, ReceiptDetails.class);
            //if (requestCode == SETTLEMENTDETAIL) intent = new Intent(this, SettlementDetails.class);

            intent.putExtra("dataId", remoteMessage.getData().get("data"));
            intent.putExtra("activity", Integer.parseInt(remoteMessage.getData().get("activity")));
            pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: Title: " + remoteMessage.getNotification().getTitle() + " Body: " + remoteMessage.getNotification().getBody());

            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            sendNotification(title, body);
        }
    }

    //create notification with intent to involved activity/fragment and display in statusbar
    private void sendNotification(String title, String messageBody) {
        Log.d(TAG, "Building Notification...");

        if (intent == null) {
            intent = new Intent(this, MainActivity.class);
            pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (title == null) title="TEST123";
        if (messageBody == null) messageBody ="TESTBESCHREIBUNG";

        String channelId = "ReceiptScan";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(requestCode  /*ID of notification */, notification.build());
    }
}
