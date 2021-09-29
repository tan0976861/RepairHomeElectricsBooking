package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}