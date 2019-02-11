package com.alphadude.user.bettingtipz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private Button Login, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button)findViewById(R.id.login);
        Back =(Button)findViewById(R.id.back);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveBack();
            }
        });
    }

    public void move(){
        Intent open = new Intent(this, AdminActivity.class);
        startActivity(open);
    }
    public void moveBack(){
        Intent open = new Intent(this, HomeScreen.class);
        startActivity(open);
    }

}
