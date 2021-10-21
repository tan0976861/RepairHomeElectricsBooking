package com.example.repairhomeelectricbooking;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    DatabaseReference mDatabaseOrder;
    private GoogleMap mMap;

//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (mViewPager.getCurrentItem() == mListPhoto.size() - 1) {
//                mViewPager.setCurrentItem(0);
//            } else {
//                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//            }
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worker_main, container, false);
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
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus()==1){
                        Intent intent= new Intent(getActivity(), MainWorkerStatusActivity.class);
//                        intent.putExtra("userName",order.getUser().getFullName());
//                        intent.putExtra("fee",order.getWorker().getFee());
                        startActivity(intent);
                        return;
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