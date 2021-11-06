package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.repairhomeelectricbooking.dto.LocationApp;
import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.OrderCache;
import com.example.repairhomeelectricbooking.dto.Rating;
import com.example.repairhomeelectricbooking.dto.Service;
import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.example.repairhomeelectricbooking.fcm.FcmNotificationsSender;
import com.example.repairhomeelectricbooking.fcm.MyFirebaseMessagingService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AnimationSearchActivity extends AppCompatActivity {
    public static final String TAG = AnimationSearchActivity.class.getName();

    String strThietBi, strVanDe, strFee,strLocationUser,strUserName,strPhoneUser,strMotathemchitiet;
    DatabaseReference mDatabase,mDatabaseUser,mDatabaseRating,mDatabaseOrder,mDatabaseToken,mDatabaseUser2;
    List<Worker> listWorkers;
    LocationApp locationUser;
    Button cancelSearchWorker;
    double ratingPoint;
    Handler handler = new Handler();
    long maxId = 0;
    List<Service> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_search);
        getDataIntent();
        String date= LocalDate.now().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference("tblWorker");
        listWorkers=null;
        System.out.println("testinfothietbi: " +strThietBi);
        String replaceStrThietBi= strThietBi.replace("\n", "-");
        System.out.println("Test replace Thiet bi: 12333 " +replaceStrThietBi);
        String[] info= replaceStrThietBi.split("-");
        System.out.println("tesst info: " +  info);
        String[] loai= replaceStrThietBi.split("\n");
        listWorkers = searchWorker(info[0]);
        System.out.println("65: + " + info[1]);
        mDatabaseOrder=FirebaseDatabase.getInstance().getReference("tblOrder");
        cancelSearchWorker = findViewById(R.id.btnCancelSearchWorker);
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG,"367"+snapshot.getChildrenCount());
                maxId = (snapshot.getChildrenCount());
                Log.e(TAG,"369"+maxId);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseUser2=FirebaseDatabase.getInstance().getReference("tblUser").child(user.getUid());
        mDatabaseUser2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User mainUser= snapshot.getValue(User.class);
                strUserName= mainUser.getFullName();
                strPhoneUser=mainUser.getPhone();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        handler = new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                if (listWorkers != null && !listWorkers.isEmpty()) {
                    createOrder(new Worker(listWorkers.get(0).getWorkerID(),listWorkers.get(0).getFullName(),listWorkers.get(0).getDistance()),new User(user.getUid(),strUserName,strLocationUser,strPhoneUser),replaceStrThietBi,strVanDe,listWorkers.get(0).getFee(),date,1);
                    createOrderCache(new Worker(listWorkers.get(0).getWorkerID(),listWorkers.get(0).getFullName(),listWorkers.get(0).getDistance()),new User(user.getUid(),strUserName,strLocationUser,strPhoneUser),replaceStrThietBi,listWorkers.get(0).getFee(),date,1);
                    sendNotiToWorker(listWorkers);
                    gotoUpdateProfile(listWorkers,strThietBi);
                }
                else {
                    gotoNoti();
                }
            }
        };
        handler.postDelayed(myRunnable,6000);

        cancelSearchWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimationSearchActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
                finish();
                handler.removeCallbacks(myRunnable);
            }
        });

    }

    private void getDataIntent() {
        strThietBi = getIntent().getStringExtra("thietbi");
        strVanDe = getIntent().getStringExtra("problem");
        strFee = getIntent().getStringExtra("price");
        strLocationUser=getIntent().getStringExtra("locationUser");
        strMotathemchitiet=getIntent().getStringExtra("problemDetail");
    }


    private List<Worker> searchWorker(String Type) {
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
                                System.out.println("Latitude User"+ localUser.getLatitude());
                                System.out.println("Longtitude User" + localUser.getLongtitude());
                                System.out.println("Longtitude Worker:  "+ worker.getLocation().getLongtitude());
                                System.out.println("Latitude Worker: "+ worker.getLocation().getLatitude());
                                double distance = distance(localUser.getLatitude(),localUser.getLongtitude(),worker.getLocation().getLongtitude(),worker.getLocation().getLatitude());
                                System.out.println("Distance: "+ distance);
                                worker.setDistance(distance);
                                if(worker.isActive()){
                                    List<Double>  ratingPointList= new ArrayList<>();
                                    mDatabaseRating=FirebaseDatabase.getInstance().getReference("tblRating");
                                    mDatabaseRating.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                                                Rating ratingWorker = postSnapshot.getValue(Rating.class);
                                                if(ratingWorker.getWorkerId().equals(worker.getWorkerID())){
                                                    ratingPointList.add(ratingWorker.getRatingPoint());
                                                }

                                            }
                                            ratingPoint=0;
                                            for (double element : ratingPointList) {
                                                ratingPoint += element;
                                            }
                                            ratingPoint=ratingPoint/ratingPointList.size();
                                            worker.setRatingPoint(ratingPoint);

                                        }

                                        @Override
                                        public void onCancelled( DatabaseError error) {

                                        }
                                    });
                                    listWorkers.add(worker);
                                }
                                Collections.sort(listWorkers);
                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return listWorkers;
    }

    private double distance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {

        float[] result = new float[1];
        Location.distanceBetween(startLatitude, startLongitude, endLongitude, endLatitude, result);
        double distance = result[0];
        double kilometer = (distance / 1000);
        int m = (int) distance;
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
                        ratingPointList.add(ratingWorker.getRatingPoint());
                    }

                }

                for (double element : ratingPointList) {
                    ratingPoint += element;
                }
                ratingPoint=ratingPoint/ratingPointList.size();
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
        intent.putExtra("distance",listWorkers.get(0).getDistance());
        startActivity(intent);
        finish();
    }

    private void createOrder(Worker worker, User user, String problem,String probemDetaoils,Double fee, String createDate, int status){
        //FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();

        getG(worker,user, maxId,problem,probemDetaoils,fee,createDate,1);
        Log.e(TAG,"376"+maxId);
    }



    public void getG(Worker worker, User user,long maxId, String problem,String probemDetaoils,Double fee, String createDate, int status){
        DatabaseReference mDatabaseOrder2=FirebaseDatabase.getInstance().getReference("tblOrder");
        Service service= new Service("Phí dịch vụ: ", fee);

        list.add(service);
        Order order = new Order(worker,user,list, maxId + 1,problem,probemDetaoils,fee,createDate,1);
        mDatabaseOrder2.child(String.valueOf(maxId + 1)).setValue(order);
    }
    private void createOrderCache(Worker worker, User user, String problem, Double fee, String createDate, int status){
        //FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();

        getG2(worker,user, maxId,problem,fee,createDate,1);
    }
    public void getG2(Worker worker, User user,long maxId, String problem, Double fee, String createDate, int status){
        DatabaseReference mDatabaseOrder2=FirebaseDatabase.getInstance().getReference("tblOrderCache");
        Service service= new Service("Phí dịch vụ: ", fee);
        list.add(service);
        OrderCache order = new OrderCache(worker,user,list, maxId + 1,problem,fee,createDate,1);
        mDatabaseOrder2.child(String.valueOf(maxId + 1)).setValue(order);
    }
    private void sendNotiToWorker(List<Worker> listWorkers){
        mDatabaseToken = FirebaseDatabase.getInstance().getReference("Tokens").child(listWorkers.get(0).getWorkerID());
        mDatabaseToken.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String token =snapshot.getValue(String.class);
                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,
                        "E-Repair Home Electric",
                        "New Order",getApplicationContext(),AnimationSearchActivity.this);
                notificationsSender.SendNotifications();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void gotoNoti() {
        Intent intent = new Intent(this,SearchWorkerActivity.class);
        startActivity(intent);
        finish();
    }

}