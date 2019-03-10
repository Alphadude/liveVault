package com.alphadude.user.bettingtipz;

import android.app.Application;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FirebaseMessaging.getInstance().subscribeToTopic("tips")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     //   String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                          //  msg = getString(R.string.msg_subscribe_failed);
                            //Toast.makeText(App.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    //    Log.d(TAG, msg);
                        //Toast.makeText(App.this, "Subscribed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
