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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddTips extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;

    private FancyButton pickDate, submitTips;
    private EditText edtCountry, edtHomeTeam, edtAwayTeam, edtTips;

    private String key, country, homeTeam, awayTeam, tips,date;

    private DatabaseReference tipsRef;
    private ProgressDialog dialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(this);

        tipsRef = FirebaseDatabase.getInstance().getReference().child("Tips");
        edtCountry = (EditText)findViewById(R.id.edtLeague);
        edtHomeTeam = (EditText)findViewById(R.id.edtHomeTeam);
        edtAwayTeam = (EditText)findViewById(R.id.edtAwayTeam);
        edtTips = (EditText)findViewById(R.id.edtTips);
        pickDate = (FancyButton)findViewById(R.id.btnDatePicker);
        submitTips = (FancyButton)findViewById(R.id.btnSubmitTip);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(AddTips.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        date =  Day + "-" + Month + "-" + Year;


    }
}
