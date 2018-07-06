package com.example.jan.kassenzettel_scan.network;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/*
Token generator for firebase messaging service. With this FCM can send push messages directly to the client.
https://firebase.google.com/docs/cloud-messaging/android/first-message
*/

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM_TOKEN", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // Currently not needed because app-server doesn't handle client tokens, only subscription topics
        // TODO: Implement this method to send token to your app server.
    }
}
