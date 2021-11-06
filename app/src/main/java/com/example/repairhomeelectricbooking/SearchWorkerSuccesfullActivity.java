package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchWorkerSuccesfullActivity extends AppCompatActivity {
    private TextView  tvFullName,tvPhoneNumber,tvFee1,tvFee2,tvLoai;
    String strFullName,strPhoneNumber,strThietBi,strUid;
      Double      strFee,strRatingPoint,strFee1,strFee2,strDistance;
      Timer time;
      CircleImageView imgSearchWorkerSuccess;
      DatabaseReference rootDatabaseref;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_worker_succesfull);
        tvFullName= (TextView)findViewById(R.id.tv_search_worker_fullName);
        tvPhoneNumber= (TextView)findViewById(R.id.tv_search_worker_sdt);
        tvFee1= (TextView)findViewById(R.id.tv_search_worker_giaTien1);
        tvFee2= (TextView)findViewById(R.id.tv_search_worker_giaTien2);
        tvLoai=(TextView)findViewById(R.id.tv_search_worker_loai) ;
//        tvRatingPoint=(TextView)findViewById(R.id.tv_search_worker_longtitude);
        imgSearchWorkerSuccess=(CircleImageView) findViewById(R.id.imgSearchWorkerSuccess);
        getDataIntent();
        String[] info= strThietBi.split("-");
        Locale localeVn= new Locale("vi","VN");
        NumberFormat nf=NumberFormat.getInstance(localeVn);
       // String.format("%,2f", strFee);
        strFee1=strFee-50000;
        strFee2=strFee+50000;
        tvFullName.setText(strFullName);
        tvFee1.setText(nf.format(strFee1));
        tvFee2.setText(nf.format(strFee2));
        String phoneReplace= "0" + strPhoneNumber.substring(3,strPhoneNumber.length());
        tvPhoneNumber.setText(phoneReplace);
        tvLoai.setText(info[0]);
//        tvRatingPoint.setText(strRatingPoint.toString());

        handler = new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                Intent intent= new Intent(SearchWorkerSuccesfullActivity.this, LocationMapActivity.class);
                intent.putExtra("full_name",strFullName);
                intent.putExtra("phoneNumber",phoneReplace);
                intent.putExtra("fee",strFee);
                intent.putExtra("ratingPont",strRatingPoint);
                intent.putExtra("distance",strDistance);
                intent.putExtra("uID",strUid);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(myRunnable,6000);

        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("tblWorker");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootDatabaseref.child(strUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.child("image").getValue().toString();
                if(link.equals("")){
                    Glide.with(SearchWorkerSuccesfullActivity.this).load(snapshot.getValue().toString()).error(R.drawable.avatar_default).into(imgSearchWorkerSuccess);
                }else{
                    Picasso.get().load(link).into(imgSearchWorkerSuccess);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchWorkerSuccesfullActivity.this, "Chưa thêm hình ảnh", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getDataIntent (){
        strPhoneNumber = getIntent().getStringExtra("phoneNumber");
        strFullName = getIntent().getStringExtra("full_name");
        strFee = getIntent().getDoubleExtra("fee",0.0d);
        strRatingPoint =getIntent().getDoubleExtra("ratingPont", 0.0d);
        strThietBi = getIntent().getStringExtra("thietbi");
        strUid= getIntent().getStringExtra("uID");
        strDistance=getIntent().getDoubleExtra("distance", 0.0d);


    }


}