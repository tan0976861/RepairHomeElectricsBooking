package com.example.repairhomeelectricsbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.printf("tan");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ódjfsdojfsdf");
    }
}