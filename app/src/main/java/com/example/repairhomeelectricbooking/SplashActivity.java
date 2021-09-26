package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
                    startActivity(DangKyActivity.class);
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
}