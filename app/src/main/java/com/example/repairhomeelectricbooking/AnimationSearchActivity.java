package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.repairhomeelectricbooking.dto.LocationApp;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnimationSearchActivity extends AppCompatActivity {
    String strThietBi,strVanDe,strFee;
    DatabaseReference mDatabase;
    List<Worker> listWorkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_search);
        getDataIntent();
        double fee = Double.parseDouble(strFee);
        mDatabase = FirebaseDatabase.getInstance().getReference("tblWorker");
       // listWorkers = searchWorker("fee");



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(listWorkers != null && !listWorkers.isEmpty()) {
                    //Main
                    gotoUpdateProfile(listWorkers);


                } else {
                    //Onboarding
                    gotoNoti();
                }

                }

        },2000);
   }
    private void getDataIntent (){
        strThietBi = getIntent().getStringExtra("thietbi");
        strVanDe = getIntent().getStringExtra("problem");
        strFee = getIntent().getStringExtra("price");

    }

//    private List<Worker> searchWorker(String Type){
//        //boolean check = false;
//        List<Worker> list= new ArrayList<>();
//        Query queryWorker= mDatabase.orderByChild("fee");
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser  = mAuth.getCurrentUser();
//        queryWorker.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    // TODO: handle the post
//                    Worker worker = postSnapshot.getValue(Worker.class);
//                    if (worker.getFee() < 200000) {
                        //worker.getLocation();
//                        DatabaseReference locationRef= mDatabase.child(postSnapshot.getKey()).child("location");
//                        locationRef.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for(DataSnapshot postSnapshot: snapshot.getChildren()){
//                                    LocationApp location= postSnapshot.getValue(LocationApp.class);
//                                    System.out.println(location.toString());
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//                        mDatabase.child("tblUser").child("location").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                distance(worker.getLocation().getLatitude(),worker.getLocation().getLongtitude(),);
//                            }
//                        });

//                        list.add(worker);
//                        System.out.println(worker.toString());
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return list;
//    }
    private void distance(double startLatitude, double startLongitude,double endLatitude ,double endLongitude  ){
//        double startLatitude = 10.7693;
//        double startLongitude = 106.6832;
//        double endLongitude = 10.7925;
//        double endLatitude = 106.6786;
        float[] result = new float[1];
        Location.distanceBetween(startLatitude,startLongitude,endLongitude,endLatitude,result);
        float distance = result[0];
        double kilometer = (distance/1000);
        int m = (int) distance;
//        if(kilometer < 1){
//            textView1.setText(m + "m");
//        }else{
//            textView1.setText((Math.ceil(kilometer * 100)/100) + "km");
//        }
    }
    private void gotoUpdateProfile( List<Worker> listWorkers) {
        Intent intent = new Intent(this,SearchWorkerSuccesfullActivity.class);
        intent.putExtra("full_name",listWorkers.get(0).getFullName());
        intent.putExtra("phoneNumber",listWorkers.get(0).getPhone());
        intent.putExtra("fee",listWorkers.get(0).getFee());
//        System.out.println("dkmm"+listWorkers.get(0).getFee());
        startActivity(intent);
    }
    private void gotoNoti() {
        Intent intent = new Intent(this,SearchWorkerActivity.class);
        startActivity(intent);
    }

}