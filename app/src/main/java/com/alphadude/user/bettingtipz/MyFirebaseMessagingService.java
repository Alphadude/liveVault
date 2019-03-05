package com.alphadude.user.bettingtipz;

import android.app.Service;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here
        notifyUser();


    }

    public void notifyUser(){
        notificationManager = new NotificationManager(getApplicationContext());
        notificationManager.shownotifiaction("ONE SLIP 2+3 ODDS 100% WIN","You have a new tip added",new Intent(getApplicationContext(),HomeScreen.class));

    }
}
