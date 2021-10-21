package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RatingActivity extends AppCompatActivity {

    Button btnGui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        btnGui=(Button) findViewById(R.id.btnSendRating);
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RatingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}