package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.repairhomeelectricbooking.dto.LocationApp;
import com.example.repairhomeelectricbooking.dto.Rating;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.example.repairhomeelectricbooking.fcm.FcmNotificationsSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimationSearchActivity extends AppCompatActivity {
    String strThietBi, strVanDe, strFee;
    DatabaseReference mDatabase,mDatabaseUser,mDatabaseRating;
    List<Worker> listWorkers;
    LocationApp locationUser;
   double ratingPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_search);
        getDataIntent();


        //double fee = Double.parseDouble(strFee);
        mDatabase = FirebaseDatabase.getInstance().getReference("tblWorker");
        //System.out.println(getRatingPointForWorker("1AQayxmAzWPgPsSrLt4ZO03yYeo1"));
//        locationUser= new LocationApp();
//        locationUser=getLocationUser();
//        System.out.println("123 ^ "+locationUser.getLongtitude());
      listWorkers = searchWorker(strThietBi);
        //System.out.println("List after search. "+ listWorkers.toString());
//      for(Worker setRatingList : listWorkers){
//
//          ratingPoint=getRatingPointForWorker(setRatingList.getWorkerId());
//          setRatingList.setRatingPoint(ratingPoint);
//      }
//        System.out.println("List after set rating: "+ listWorkers.toString());




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listWorkers != null && !listWorkers.isEmpty()) {
                    //Main
                    sendFormOrderForWorker(listWorkers);
                    gotoUpdateProfile(listWorkers,strThietBi);
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("cC841uQ5RJuyoRxtj30LXe:APA91bGofn0rJWgVUMa4cMC00DeiNPiINuuFHqcRU87KhxR9VUUStfIESuzoBxBcCiX1HibrEZpHQfDp2d7WFg4HeieSoc5LsWglZ45vUKqidpfcIjUdL5BWG2LOmkvDnyr_1kUvjnKp",
                            "TNT3",
                            "Notification",getApplicationContext(),AnimationSearchActivity.this);
                    notificationsSender.SendNotifications();


              }
                else {
                    //Onboarding
                    gotoNoti();
                }

            }

        }, 5000);
    }

    private void getDataIntent() {
        strThietBi = getIntent().getStringExtra("thietbi");
        strVanDe = getIntent().getStringExtra("problem");
        strFee = getIntent().getStringExtra("price");

    }
//    private LocationApp getLocationUser(){
//
//
//    }

    private List<Worker> searchWorker(String Type) {
        //boolean check = false;
        //List<Worker> list = new ArrayList<Worker>();
        listWorkers = new ArrayList<Worker>();
        Query queryWorker = mDatabase.orderByChild("fee");
        queryWorker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Worker worker = postSnapshot.getValue(Worker.class);
                    if (worker.getType().contains(Type)) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        mDatabaseUser=FirebaseDatabase.getInstance().getReference("tblUser/" + firebaseUser.getUid() + "/location");
                        mDatabaseUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange( DataSnapshot snapshot) {
                                LocationApp localUser = snapshot.getValue(LocationApp.class);
                                // System.out.println("Local User: "+localUser.getLongtitude());
                                //locationUser=localUser;
                                System.out.println("Latitude User"+ localUser.getLatitude());
                                System.out.println("Longtitude User" + localUser.getLongtitude());
                                System.out.println("Longtitude Worker:  "+ worker.getLocation().getLongtitude());
                                System.out.println("Latitude Worker: "+ worker.getLocation().getLatitude());
                               double distance = distance(localUser.getLatitude(),localUser.getLongtitude(),worker.getLocation().getLongtitude(),worker.getLocation().getLatitude());
                                System.out.println("Distance: "+ distance);
                               worker.setDistance(distance);
                               if(worker.isActive()){
//                                   double ratingPonint=getRatingPointForWorker(worker.getWorkerId());
                                   List<Double>  ratingPointList= new ArrayList<>();
                                   mDatabaseRating=FirebaseDatabase.getInstance().getReference("tblRating");
                                   mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot snapshot) {
                                           for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                                               Rating ratingWorker = postSnapshot.getValue(Rating.class);
                                               if(ratingWorker.getWorkerId().equals(worker.getWorkerID())){

                                                   System.out.println("CustomerId= " + ratingWorker.getCustomerId());
                                                   System.out.println("WorkerId= " + ratingWorker.getWorkerId());
                                                   System.out.println("Comment= " + ratingWorker.getComment());
                                                   System.out.println("Ratting point = " + ratingWorker.getRatingPoint());
                                                   ratingPointList.add(ratingWorker.getRatingPoint());
                                                   System.out.println("RatingPoint List: " + ratingPointList);
                                               }

                                           }
                                           ratingPoint=0;
                                           for (double element : ratingPointList) {
                                               ratingPoint += element;
                                               System.out.println("Rating Point in  for: " +ratingPoint);
                                           }
                                           ratingPoint=ratingPoint/ratingPointList.size();
                                           System.out.println("fianlRating Point: "+ ratingPoint);
                                           worker.setRatingPoint(ratingPoint);

                                       }

                                       @Override
                                       public void onCancelled( DatabaseError error) {

                                       }
                                   });
                                   System.out.println("@#@zcx"+worker.getWorkerID());
                                  // System.out.println("ratingPoint : "+ ratingPonint);
                                   //worker.setRatingPoint(getRatingPointForWorker(worker.getWorkerId()));

                                   listWorkers.add(worker);
                                   System.out.println(worker.toString());
                               }


                                Collections.sort(listWorkers);
                                System.out.println("@#@##2 "+listWorkers);


                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                }


//               Collections.sort(list, new Comparator<Worker>() {
//                   @Override
//                   public int compare(Worker worker, Worker t1) {
//                       //sap xep distance tang dan
//                       if (worker.distance < t1.distance) {
//                           return -1;
//                       } else {
//                           if (worker.distance == t1.distance) {
//                               return 0;
//
//                           } else {
//                               return -1;
//                           }
//                       }
//                   }
//               });

           }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return listWorkers;
    }

    private double distance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
//        double startLatitude = 10.7693;
//        double startLongitude = 106.6832;
//        double endLongitude = 10.7925;
//        double endLatitude = 106.6786;
        float[] result = new float[1];
        Location.distanceBetween(startLatitude, startLongitude, endLongitude, endLatitude, result);
        double distance = result[0];
        double kilometer = (distance / 1000);
        int m = (int) distance;
//        if(kilometer < 1){
//            textView1.setText(m + "m");
//        }else{
//            textView1.setText((Math.ceil(kilometer * 100)/100) + "km");
//        }
        return distance;
    }
    private double getRatingPointForWorker(String WorkerId){
         //ratingPointList;
                List<Double>  ratingPointList= new ArrayList<>();
                mDatabaseRating=FirebaseDatabase.getInstance().getReference("tblRating");
                mDatabaseRating.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                            Rating ratingWorker = postSnapshot.getValue(Rating.class);
                            if(ratingWorker.getWorkerId().equals(WorkerId)){

                                System.out.println("CustomerId= " + ratingWorker.getCustomerId());
                                System.out.println("WorkerId= " + ratingWorker.getWorkerId());
                                System.out.println("Comment= " + ratingWorker.getComment());
                                System.out.println("Ratting point = " + ratingWorker.getRatingPoint());
                                ratingPointList.add(ratingWorker.getRatingPoint());
                                System.out.println("RatingPoint List: " + ratingPointList);
                            }

                        }

                        for (double element : ratingPointList) {
                            ratingPoint += element;
                        }
                        ratingPoint=ratingPoint/ratingPointList.size();
                        System.out.println("fianlRating Point: "+ ratingPoint);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return ratingPoint;
    }


    private void gotoUpdateProfile( List<Worker> listWorkers,String strThietBi) {
        Intent intent = new Intent(this,SearchWorkerSuccesfullActivity.class);
        intent.putExtra("full_name",listWorkers.get(0).getFullName());
        intent.putExtra("phoneNumber",listWorkers.get(0).getPhone());
        intent.putExtra("fee",listWorkers.get(0).getFee());
        intent.putExtra("ratingPoint", listWorkers.get(0).getRatingPoint());
        intent.putExtra("thietbi",strThietBi);
        intent.putExtra("uID",listWorkers.get(0).getWorkerID());
        startActivity(intent);
    }
    private void sendFormOrderForWorker(List<Worker> listWorkers){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
      WorkerMainFragment workerMainFragment= new WorkerMainFragment();
        NotificationFragment notificationFragment= new NotificationFragment();
       Bundle bundle= new Bundle();
       bundle.putString("worker_ReceiveOrder", listWorkers.get(0).getEmail() );
        notificationFragment.setArguments(bundle);


        fragmentTransaction.commit();

    }
    private void gotoNoti() {
        Intent intent = new Intent(this,SearchWorkerActivity.class);
        startActivity(intent);
    }

}