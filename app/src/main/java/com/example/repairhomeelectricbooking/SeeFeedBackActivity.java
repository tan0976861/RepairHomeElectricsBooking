package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.repairhomeelectricbooking.adapter.FeedBackAdapter;
import com.example.repairhomeelectricbooking.adapter.HistoryWorkerAdapter;
import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.Rating;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeFeedBackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton imgBackToMainWorkerFragment;
    FeedBackAdapter myAdapter;
    ArrayList<Rating> list;

    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_feed_back);
        database = FirebaseDatabase.getInstance().getReference("tblRating");
        recyclerView =  findViewById(R.id.feedbackreviewList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SeeFeedBackActivity.this));
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        list = new ArrayList<>();
        myAdapter = new FeedBackAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Rating rating = dataSnapshot.getValue(Rating.class);
                    if(rating.getWorkerId().equals(firebaseUser.getUid())){
                        list.add(rating);
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgBackToMainWorkerFragment=(ImageButton) findViewById(R.id.imgBackToMainWorkerFragment);
        imgBackToMainWorkerFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }
}