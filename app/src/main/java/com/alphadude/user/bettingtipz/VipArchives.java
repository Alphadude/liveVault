package com.alphadude.user.bettingtipz;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alphadude.user.bettingtipz.Model.Archive;
import com.alphadude.user.bettingtipz.Model.Tips;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VipArchives extends AppCompatActivity {

    private RecyclerView archiveList;
    private DatabaseReference archiveRef;
    private LinearLayoutManager mLayoutManager;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_archives);

        archiveList = (RecyclerView)findViewById(R.id.rvTips);
        archiveRef = FirebaseDatabase.getInstance().getReference().child("Archives");

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);

        archiveRef.keepSynced(true);
        archiveList.setHasFixedSize(true);
        archiveList.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAdapter();
    }

    private void initAdapter() {
        FirebaseRecyclerAdapter<Archive,PostViewHolder> adapter = new FirebaseRecyclerAdapter<Archive, PostViewHolder>(
                Archive.class,
                R.layout.tips,
               PostViewHolder.class,
                archiveRef
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Archive model, int position) {


                viewHolder.setDate(model.getDate());

                viewHolder.odds.setText(model.getOdds());

                if (model.getResult().equalsIgnoreCase("win")){
                    viewHolder.results.setTextColor(Color.parseColor("#008577"));
                }else{
                    viewHolder.results.setTextColor(Color.parseColor("FFF5382E"));
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
