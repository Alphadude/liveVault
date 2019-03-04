package com.alphadude.user.bettingtipz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alphadude.user.bettingtipz.Model.Tips;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.pusher.pushnotifications.PushNotifications;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddTips extends AppCompatActivity implements  CalendarDatePickerDialogFragment.OnDateSetListener {

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;

    private FancyButton pickDate, submitTips;
    private EditText edtCountry, edtHomeTeam, edtAwayTeam, edtTips;

    private String key, country, homeTeam, awayTeam, tips,date;

    private DatabaseReference tipsRef,notificationRef;
    private ProgressDialog dialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(this);

        tipsRef = FirebaseDatabase.getInstance().getReference().child("Tips");
        notificationRef = FirebaseDatabase.getInstance().getReference().child("Notification");
        edtCountry = findViewById(R.id.edtLeague);
        edtHomeTeam = findViewById(R.id.edtHomeTeam);
        edtAwayTeam = findViewById(R.id.edtAwayTeam);
        edtTips = findViewById(R.id.edtTips);
        pickDate = findViewById(R.id.btnDatePicker);
        submitTips = findViewById(R.id.btnSubmitTip);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(AddTips.this);
                cdp.show(getSupportFragmentManager(), "Date piker");
            }
        });

        submitTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadTip();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void uploadTip() {
        dialog.setMessage("Submitting tips..please wait");
        dialog.setCancelable(false);
        dialog.show();


        country = edtCountry.getText().toString().trim();
        homeTeam = edtHomeTeam.getText().toString().trim();
        awayTeam = edtAwayTeam.getText().toString().trim();
        tips = edtTips.getText().toString().trim();

        if (date.isEmpty()){
            Toast.makeText(this, "please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference booksRef = tipsRef.push();
        key = booksRef.getKey();


        Tips model = new  Tips();

        model.setCountry(country);
        model.setDate(date);
        model.setResult("");
        model.setTeamAway(awayTeam);
        model.setTeamHome(homeTeam);
        model.setKey(key);
        model.setTips(tips);

        booksRef.setValue(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Map<String, String> fcmNotification = new HashMap();
                            fcmNotification.put("title", "ONE SLIP 2+3 ODDS 100% WIN");
                            fcmNotification.put("body", " New Tip Added");


                            notificationRef.push().setValue(fcmNotification)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                dialog.dismiss();
                                                Toast.makeText(AddTips.this, "Successfully added tip", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AddTips.this,AdminActivity.class));
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(AddTips.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(AddTips.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear+1;
            date = String.valueOf(dayOfMonth + "-"+month+"-"+year);
    }
}
