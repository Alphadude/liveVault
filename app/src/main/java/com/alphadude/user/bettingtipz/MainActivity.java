package com.alphadude.user.bettingtipz;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

ActionBar actionBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME );
        setContentView(R.layout.activity_main);

        ImageView splashImage = findViewById(R.id.splash);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        splashImage.startAnimation(animation);

        final Intent intent = new Intent(this,HomeScreen.class);

        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
