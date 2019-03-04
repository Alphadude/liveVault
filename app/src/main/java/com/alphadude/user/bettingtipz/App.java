package com.alphadude.user.bettingtipz;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.pusher.pushnotifications.PushNotifications;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        PushNotifications.start(getApplicationContext(), "300dac22-3ace-4280-97e5-9eb0b53d7cf9");
        PushNotifications.addDeviceInterest("tips");
    }

}
