package com.alphadude.user.bettingtipz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    private Button freetips, vipsub, viparchives;
    private Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        freetips = (Button) findViewById(R.id.freetips);
        vipsub = (Button) findViewById(R.id.vipsub);
        viparchives = (Button) findViewById(R.id.viparchives);

        admin = (Button) findViewById(R.id.here);

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

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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

    public void login(){
        Intent open = new Intent(this, LoginActivity.class);
        startActivity(open);
    }
}
