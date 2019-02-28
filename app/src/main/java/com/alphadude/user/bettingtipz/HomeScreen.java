package com.alphadude.user.bettingtipz;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    private Button freetips, vipsub, viparchives, Rate;
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
        Rate =  findViewById(R.id.rate);




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

        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Viparchives();
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

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        //respond to menu item selection

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
