package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SearchWorkerSuccesfullActivity extends AppCompatActivity {
    private TextView  tvFullName,tvPhoneNumber,tvFee;
    String strFullName,strPhoneNumber;
      Double      strFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_worker_succesfull);
        tvFullName= (TextView)findViewById(R.id.tv_search_worker_fullName);
        tvPhoneNumber= (TextView)findViewById(R.id.tv_search_worker_sdt);
        tvFee= (TextView)findViewById(R.id.tv_search_worker_giaTien);
        getDataIntent();
        tvFullName.setText(strFullName);
        tvFee.setText(strFee.toString());
        tvPhoneNumber.setText(strPhoneNumber);
    }
    private void getDataIntent (){
        strPhoneNumber = getIntent().getStringExtra("phoneNumber");
        strFullName = getIntent().getStringExtra("full_name");
        strFee = getIntent().getDoubleExtra("fee",0.0d);

    }

}