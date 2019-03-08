package com.alphadude.user.bettingtipz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeScreen extends AppCompatActivity {

    private Button freetips, vipsub, viparchives;
    ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setIcon(R.mipmap.icon);
        setContentView(R.layout.activity_home_screen);

        freetips = findViewById(R.id.freetips);
        vipsub =  findViewById(R.id.vipsub);
        viparchives =  findViewById(R.id.viparchives);
        //Rate =  findViewById(R.id.rate);




        freetips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFreeTips();
            }
        });

        vipsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vipSub();
            }
        });

        viparchives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Viparchives();
            }
        });

//        Rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rateMe();
//            }
//        });
        FirebaseMessaging.getInstance().subscribeToTopic("tips")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //   String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //  msg = getString(R.string.msg_subscribe_failed);
                            Toast.makeText(HomeScreen.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                        //    Log.d(TAG, msg);
                        Toast.makeText(HomeScreen.this, "Subscribed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void openFreeTips(){
        Intent open = new Intent(this, TipActivity.class);
        startActivity(open);
    }

    public void vipSub(){
        Intent open = new Intent(this, VipSub.class);
        startActivity(open);
    }

    public void Viparchives(){
        Intent open = new Intent(this, VipArchives.class);
        startActivity(open);
    }

    public void rateMe(View v){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + "com.android.chrome")));
        } catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + "com.android.chrome")));
        }
    }

    public void login(){
        Intent open = new Intent(this, LoginActivity.class);
        startActivity(open);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseMessaging.getInstance().subscribeToTopic("tips")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //   String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //  msg = getString(R.string.msg_subscribe_failed);
                            Toast.makeText(HomeScreen.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                        //    Log.d(TAG, msg);
                        Toast.makeText(HomeScreen.this, "Subscribed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public boolean onOptionsItemSelected(MenuItem item) {


        //respond to menu item selection

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                return true;

            case R.id.notification:
                startActivity(new Intent(this, NotifyActivity.class));
                return true;
            case R.id.share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = "hello welcome to one slip 2+3 odds download from www.helixgade.com ";
                //String shareTopic ="header to see what works";
                share.putExtra(Intent.EXTRA_TEXT,shareBody);
                //share.putExtra(Intent.EXTRA_SUBJECT,shareTopic);
                startActivity(Intent.createChooser(share,"Share One Slip 2+3 Odds 100% win"));
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
