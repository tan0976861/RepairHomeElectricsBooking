package com.example.repairhomeelectricbooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.example.repairhomeelectricbooking.photo.Photo;
import com.example.repairhomeelectricbooking.photo.PhotoViewPagerWorkerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class WorkerMainFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener{

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Photo> mListPhoto;
    private Handler mHandler = new Handler();
    private Worker worker;
    private TextView tvTest1;
    private TextView edtTest;
    private String emailTest;
    private Switch sw_on_of_activity;
    DatabaseReference mDatabaseOrder;
    private GoogleMap mMap;
    //private RelativeLayout layoutSeeFeedback;
    MainWorkerActivity activity;
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = (MainWorkerActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worker_main, container, false);

       // layoutSeeFeedback=(RelativeLayout) view.findViewById(R.id.layoutSeeFeedback);


        DatabaseReference mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblWorker");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseOrder2= FirebaseDatabase.getInstance().getReference("tblWorker").child(firebaseUser.getUid()).child("active");
        mDatabaseOrder2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean value = snapshot.getValue(Boolean.class);
                if(value== true){
                    sw_on_of_activity.setChecked(true);
                }else{
                    sw_on_of_activity.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sw_on_of_activity = view.findViewById(R.id.switch_on_off);
        sw_on_of_activity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    mDatabaseOrder.child(firebaseUser.getUid()).child("active").setValue(true);
                    Toast.makeText(getActivity(), "Bật hoạt động", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabaseOrder.child(firebaseUser.getUid()).child("active").setValue(false);
                    Toast.makeText(getActivity(), "Tắt hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapWorker);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng latLng = new LatLng(10.7695, 106.6825);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Vị trí của bạn");
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f));
                googleMap.addMarker(markerOptions).showInfoWindow();
            }
        });
        tvTest1=(TextView) view.findViewById(R.id.txtNameWorker);
        getOrderPresent();

        return view;
    }
    private void getOrderPresent(){
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) &&  order.getStatus()==1){
                        if(isAdded()){
                            Intent intent= new Intent(activity, MainWorkerStatusActivity.class);
                            startActivity(intent);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

    }
}