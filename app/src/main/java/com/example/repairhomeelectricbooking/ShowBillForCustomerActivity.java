package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowBillForCustomerActivity extends AppCompatActivity {
Button btnAcceptBillToRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_for_customer);
        btnAcceptBillToRating=(Button) findViewById(R.id.btnAcceptBillToRating);
        btnAcceptBillToRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowBillForCustomerActivity.this,RatingActivity.class);
                startActivity(intent);
            }
        });
    }
}