package com.alphadude.user.bettingtipz;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alphadude.user.bettingtipz.Model.Tips;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotifyActivity extends AppCompatActivity {

    private RecyclerView tipsList;
    private DatabaseReference TipsRef;
    private LinearLayoutManager mLayoutManager;
    private String key;
    ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setIcon(R.mipmap.icon);
        setContentView(R.layout.activity_notify);

        tipsList = findViewById(R.id.rvNotification);
        TipsRef = FirebaseDatabase.getInstance().getReference().child("Notification");

        mLayoutManager = new LinearLayoutManager(this);

        TipsRef.keepSynced(true);
        tipsList.setHasFixedSize(true);
        tipsList.setLayoutManager(mLayoutManager);
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
        FirebaseRecyclerAdapter<Notification,PostViewHolder> adapter = new FirebaseRecyclerAdapter<Notification, PostViewHolder>(
                Notification.class,
                R.layout.single_notification_layout,
               PostViewHolder.class,
                TipsRef
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Notification model, int position) {

                viewHolder.setBody(model.getBody());
                viewHolder.setDate(model.getDate());
                viewHolder.setTitle(model.getTitle());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(NotifyActivity.this,TipActivity.class));
                    }
                });

            }

        };
        tipsList.setAdapter(adapter);
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView date,title,body;
        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            date = mView.findViewById(R.id.notDate);
            title = mView.findViewById(R.id.notTitle);
            body = mView.findViewById(R.id.notBody);



        }



        public void setDate(String name) {

            date.setText(name);
        }

        public void setTitle(String name) {

            title.setText(name);
        }

        public void setBody(String name) {

            body.setText(name);
        }


    }
}
