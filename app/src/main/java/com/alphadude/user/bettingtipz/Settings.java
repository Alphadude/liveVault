package com.alphadude.user.bettingtipz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mehdi.sakout.fancybuttons.FancyButton;

public class Settings extends AppCompatActivity {


    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        login = findViewById(R.id.betwisely);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,LoginActivity.class));
            }
        });
    }
}
