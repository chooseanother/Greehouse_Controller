package com.example.greehousecontroller.utils;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class GreenhouseFirebaseMessagingService extends FirebaseMessagingService {

    public GreenhouseFirebaseMessagingService() {
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token){
        Log.d("FCM-newToken", "Refreshed token: "+token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

        // how
        //sendRegistrationToServer(token);
    }

    public void subscribeToGreenhouse(String greenhouseId){
        FirebaseMessaging.getInstance().subscribeToTopic(greenhouseId)
                .addOnCompleteListener(task -> {
                    String msg = "successfully subscribed to greenhouse " + greenhouseId;
                    if (!task.isSuccessful()) {
                        msg = "failed to subscribe to greenhouse " + greenhouseId;
                    }
                    Log.d("GreenhouseIDVM-sub", msg);
                });
    }

    public void unsubscribeFromGreenhouse(String greenhouseId) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(greenhouseId)
                .addOnCompleteListener(task -> {
                    String msg = "successfully unsubscribed from greenhouse " + greenhouseId;
                    if (!task.isSuccessful()) {
                        msg = "failed to unsubscribe from greenhouse " + greenhouseId;
                    }
                    Log.d("GreenhouseIDVM-unsub", msg);

                });
    }
}
