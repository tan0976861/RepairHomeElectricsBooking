package com.example.repairhomeelectricsbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SearchWorkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_worker);
    }

    public void clickToBackMainScreen(View view){
        finish();
    }
}