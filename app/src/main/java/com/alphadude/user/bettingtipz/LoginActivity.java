package com.alphadude.user.bettingtipz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button Login, Back;
    private EditText edtEmail,edtPaasword;
    private String email,password;
    private FirebaseAuth auth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        Login = (Button)findViewById(R.id.login);
        Back =(Button)findViewById(R.id.back);
        edtEmail = (EditText)findViewById(R.id.loginEmail);
        edtPaasword = (EditText)findViewById(R.id.loginPassword);
        dialog = new ProgressDialog(this);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFirebaseLogin();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveBack();
            }
        });
    }

    private void startFirebaseLogin() {
        dialog.setMessage("Login you in.. please wait");
        dialog.setCancelable(false);
        dialog.show();

        email = edtEmail.getText().toString().trim();
        password = edtPaasword.getText().toString().trim();

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            move();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
