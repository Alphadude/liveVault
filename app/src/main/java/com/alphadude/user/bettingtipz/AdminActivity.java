package com.alphadude.user.bettingtipz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphadude.user.bettingtipz.Model.Archive;
import com.alphadude.user.bettingtipz.Model.Tips;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

public class AdminActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RecyclerView tipsList;
    private DatabaseReference TipsRef,archiveRef;
    private LinearLayoutManager mLayoutManager;
    private String key,date,odde,results;
    private ProgressDialog progressDialog;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(AdminActivity.this);
        tipsList = (RecyclerView)findViewById(R.id.rvAdminTIps);
        TipsRef = FirebaseDatabase.getInstance().getReference().child("Tips");
        archiveRef = FirebaseDatabase.getInstance().getReference().child("Archives");

        mLayoutManager = new LinearLayoutManager(this);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        TipsRef.keepSynced(true);
        tipsList.setHasFixedSize(true);
        tipsList.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showAlert();
            }
        });
    }

    private void showAlert() {

        String textToShow[] = {"Add Tip","Add Archive"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        builder.setItems(textToShow, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                switch(which){
                    case 0:
                        //Do this and this
                        startActivity(new Intent(AdminActivity.this,AddTips.class));

                        break;
                    case 1:
                        //Do this and this
                        final Dialog dialogArchive = new Dialog(AdminActivity.this);
                        dialogArchive.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogArchive.setContentView(R.layout.addarchives);
                        final EditText odds = dialogArchive.findViewById(R.id.edtOdds);
                        final EditText result = dialogArchive.findViewById(R.id.edtResultArc);
                        FancyButton buttonSubmit = dialogArchive.findViewById(R.id.btnSubmitResult);
                        FancyButton datePicker = dialogArchive.findViewById(R.id.btnSubmitResult);

                        datePicker.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datePickerDialog = DatePickerDialog.newInstance(AdminActivity.this, Year, Month, Day);

                                datePickerDialog.setThemeDark(false);

                                datePickerDialog.showYearPickerFirst(false);

                                datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                            }
                        });

                        buttonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressDialog.setMessage("Adding to archive");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                odde = odds.getText().toString().trim();
                                results = result.getText().toString().trim();

                                if (date.isEmpty() || odde.isEmpty()||results.isEmpty()){

                                    Archive model = new Archive();

                                    model.setDate(date);
                                    model.setOdds(odde);
                                    model.setResult(results);


                                    archiveRef.push().setValue(model)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(AdminActivity.this, "Archive uploaded successfully", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        dialogArchive.dismiss();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            dialogArchive.dismiss();
                                            Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                }else {
                                    Toast.makeText(AdminActivity.this, "Fields must be filled to complete", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                            }
                        });






                        dialogArchive.show();


                        break;

                    default: //For all other cases, do this        break;
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void initAdapter() {
        FirebaseRecyclerAdapter<Tips,PostViewHolder> adapter = new FirebaseRecyclerAdapter<Tips,PostViewHolder>(
                Tips.class,
                R.layout.tips,
               PostViewHolder.class,
                TipsRef
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, final Tips model, int position) {


                viewHolder.more.setVisibility(View.VISIBLE);


                viewHolder.setDate(model.getDate());
                viewHolder.setCountry(model.getCountry());
                viewHolder.setStatus(model.getResult());
                viewHolder.setTips(model.getTips());
                viewHolder.setTeams(model.getTeamHome()+ " vs "+model.getTeamAway());

                viewHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(AdminActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.addstatus);
                        final EditText status = dialog.findViewById(R.id.edtResult);
                        FancyButton buttonSubmit = dialog.findViewById(R.id.btnSubmitResult);

                        buttonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressDialog.setMessage("Adding tip");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                String result = status.getText().toString().trim();

                                TipsRef.child(model.getKey()).child("result").setValue(result)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    progressDialog.dismiss();
                                                    dialog.dismiss();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        progressDialog.dismiss();
                                        Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                        dialog.show();


                    }
                });


            }
        };
        tipsList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdapter();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date =  Day + "-" + Month + "-" + Year;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView date,country,teams,tips,status;
        ImageView more;

        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            date = mView.findViewById(R.id.userDate);
            country = mView.findViewById(R.id.countries);
            teams = mView.findViewById(R.id.list_desc);
            tips = mView.findViewById(R.id.tip);
            status = mView.findViewById(R.id.tipsResult);
            more = mView.findViewById(R.id.imgMore);


        }



        public void setDate(String name) {

            date.setText(name);
        }

        public void setCountry(String name) {

            country.setText(name);
        }

        public void setTeams(String name) {

            teams.setText(name);
        }

        public void setTips(String name) {

            tips.setText(name);
        }

        public void setStatus(String name) {

            status.setText(name);
        }





    }

}
