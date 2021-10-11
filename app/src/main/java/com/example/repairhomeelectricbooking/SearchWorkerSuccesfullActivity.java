package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.repairhomeelectricbooking.dto.Worker;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchWorkerSuccesfullActivity extends AppCompatActivity {
    private TextView  tvFullName,tvPhoneNumber,tvFee;
    String strFullName,strPhoneNumber;
      Double      strFee,strRatingPoint;
      Timer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_worker_succesfull);
        tvFullName= (TextView)findViewById(R.id.tv_search_worker_fullName);
        tvPhoneNumber= (TextView)findViewById(R.id.tv_search_worker_sdt);
        tvFee= (TextView)findViewById(R.id.tv_search_worker_giaTien);
//        tvRatingPoint=(TextView)findViewById(R.id.tv_search_worker_longtitude);
        getDataIntent();
        tvFullName.setText(strFullName);
        tvFee.setText(strFee.toString());
        tvPhoneNumber.setText(strPhoneNumber);
//        tvRatingPoint.setText(strRatingPoint.toString());

        time= new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent= new Intent(SearchWorkerSuccesfullActivity.this, LocationMapActivity.class);
                intent.putExtra("full_name",strFullName);
                intent.putExtra("phoneNumber",strPhoneNumber);
                intent.putExtra("fee",strFee);
                intent.putExtra("ratingPont",strRatingPoint);
                startActivity(intent);

            }
        },3000);

    }
    private void getDataIntent (){
        strPhoneNumber = getIntent().getStringExtra("phoneNumber");
        strFullName = getIntent().getStringExtra("full_name");
        strFee = getIntent().getDoubleExtra("fee",0.0d);
        strRatingPoint =getIntent().getDoubleExtra("ratingPont", 0.0d);

    }
//    private void goToLocationMap() {
//        Intent intent = new Intent(this,SearchWorkerSuccesfullActivity.class);
//        intent.putExtra("full_name",strFullName);
//        intent.putExtra("phoneNumber",strPhoneNumber);
//        intent.putExtra("fee",strFee);
//        startActivity(intent);
//    }

}