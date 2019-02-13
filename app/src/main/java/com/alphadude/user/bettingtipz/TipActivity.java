package com.alphadude.user.bettingtipz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alphadude.user.bettingtipz.Model.Tips;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TipActivity extends AppCompatActivity {


    private RecyclerView tipsList;
    private DatabaseReference TipsRef;
    private LinearLayoutManager mLayoutManager;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);


        tipsList = (RecyclerView)findViewById(R.id.rvTips);
        TipsRef = FirebaseDatabase.getInstance().getReference().child("Tips");

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);

        TipsRef.keepSynced(true);
        tipsList.setHasFixedSize(true);
        tipsList.setLayoutManager(mLayoutManager);
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

    private void initAdapter() {
        FirebaseRecyclerAdapter<Tips,PostViewHolder> adapter = new FirebaseRecyclerAdapter<Tips, PostViewHolder>(
                Tips.class,
                R.layout.tips,
                PostViewHolder.class,
                TipsRef
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Tips model, int position) {


                viewHolder.setDate(model.getDate());
                viewHolder.setCountry(model.getCountry());
                viewHolder.setStatus(model.getResult());
                viewHolder.setTips(model.getTips());
                viewHolder.setTeams(model.getTeamHome()+ " vs "+model.getTeamAway());


            }
        };
        tipsList.setAdapter(adapter);
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView date,country,teams,tips,status;

        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            date = mView.findViewById(R.id.userDate);
            country = mView.findViewById(R.id.countries);
            teams = mView.findViewById(R.id.list_desc);
            tips = mView.findViewById(R.id.tip);
            status = mView.findViewById(R.id.tipsResult);


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
