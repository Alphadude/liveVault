package com.alphadude.user.bettingtipz;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alphadude.user.bettingtipz.Model.Archive;
import com.alphadude.user.bettingtipz.Model.Tips;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class VipArchives extends AppCompatActivity {

    private RecyclerView archiveList;
    private DatabaseReference archiveRef;
    private LinearLayoutManager mLayoutManager;
    private String key;
    ActionBar actionBar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setIcon(R.mipmap.icon);
        setContentView(R.layout.activity_vip_archives);

        archiveList = (RecyclerView)findViewById(R.id.rvArchives);
        archiveRef = FirebaseDatabase.getInstance().getReference().child("Archives");

        mLayoutManager = new LinearLayoutManager(this);
      //  mLayoutManager.setReverseLayout(true);

        archiveRef.keepSynced(true);
        archiveList.setHasFixedSize(true);
        archiveList.setLayoutManager(mLayoutManager);

        FirebaseMessaging.getInstance().subscribeToTopic("tips")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()) {

                        }

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdapter();
        FirebaseMessaging.getInstance().subscribeToTopic("tips")

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {

                        }

                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        initAdapter();
    }

    private void initAdapter() {
        FirebaseRecyclerAdapter<Archive,PostViewHolder> adapter = new FirebaseRecyclerAdapter<Archive, PostViewHolder>(
                Archive.class,
                R.layout.singlearchive,
               PostViewHolder.class,
                archiveRef
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Archive model, int position) {


                viewHolder.setDate(model.getDate());

                viewHolder.odds.setText(model.getOdds());

                if (model.getResult().equalsIgnoreCase("won")){
                    viewHolder.results.setTextColor(Color.parseColor("#008577"));
                    viewHolder.results.setText(model.getResult());
                }else{
                    viewHolder.results.setText(model.getResult());
                    viewHolder.results.setTextColor(Color.parseColor("#008577"));
                }



            }
        };
        archiveList.setAdapter(adapter);
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView date,odds,results;

        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            date = mView.findViewById(R.id.TextViewDate);
            odds = mView.findViewById(R.id.TextViewOdds);
            results = mView.findViewById(R.id.TextViewResults);


        }



        public void setDate(String name) {

            date.setText(name);
        }

        public void setOdds(String name) {

            odds.setText(name);
        }

        public void setResults(String name) {

            results.setText(name);
        }


    }
}
