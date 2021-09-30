package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private static final String KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SharedPreferencesUser sharedPreferencesUser = new SharedPreferencesUser(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferencesUser.getBooleanValue(KEY_FIRST_INSTALL)){
                    //Main
                    nextActivity();
                }else{
                    //Onboarding
                    startActivity(OnboardingActivity.class);
                    sharedPreferencesUser.putBooleanValue(KEY_FIRST_INSTALL,true);
                }
            }
        },2000);
    }

    private void startActivity(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        finish();
    }
    private void nextActivity(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tblUser");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        User user = postSnapshot.getValue(User.class);
                        if(user.getEmail().equals(firebaseUser.getEmail())){
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                            return;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    // ...
                }
            });
            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("tblWorker");
            mDatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Worker worker = postSnapshot.getValue(Worker.class);
                        if(worker.getEmail().equals(firebaseUser.getEmail())){
                            Intent intent = new Intent(SplashActivity.this, MainWorkerActivity.class);
                            startActivity(intent);
                            finishAffinity();
                            return;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    // ...
                }
            });
        }
    }
}