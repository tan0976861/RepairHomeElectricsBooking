package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.OrderCache;
import com.example.repairhomeelectricbooking.dto.Rating;
import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.fcm.MyFirebaseMessagingService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocationMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    public static final String TAG = LocationMapActivity.class.getName();
    Timer time;
    //google map object
    private GoogleMap mMap;
    DatabaseReference mDatabaseOrder;
    Button btnCancelRealOrderOfCustomer;
    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;

    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;

    //polyline object
    private List<Polyline> polylines = null;
    TextView  tvNameWorker,tvRatingPoint,tvKm;
    ImageButton btnBack;
    String strNameWorker,strPhoneWorker,strUID;
    double strRatingPoint,strDistance;
    CircleImageView imgWorkerLocationMap;
    DatabaseReference rootDatabaseref;

    //phone
    private TextView tv_PhoneNumber;
    private ImageButton btnCallWorker;
    RadioButton rb_lydokhaccuacustomer;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    private static final String LOG_TAG = "AndroidExample";

    //dialog
    private Button btnCancelOrderCustomer;
    private ImageView imgCloseDialog;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        tvKm= findViewById(R.id.km);
        getDataIntent ();
        System.out.println(strDistance);
        if(strDistance != 0){
            double kilometer = (strDistance / 1000);
            int m = (int) strDistance;
            if(kilometer < 1){
                tvKm.setText(m + "m");
            }else{
                tvKm.setText((Math.ceil(kilometer * 100)/100) + "km");
        }
        }
        tvNameWorker= findViewById(R.id.txtNameWorker);
        tvRatingPoint=findViewById(R.id.txtPointStar);
        btnCallWorker=findViewById(R.id.btnCallWorker);
        tv_PhoneNumber=findViewById(R.id.tv_PhoneNumber);
        btnCancelOrderCustomer=findViewById(R.id.btnCancelOrderCustomer);
        imgWorkerLocationMap= findViewById(R.id.imgWorkerLocationMap);
        imgCloseDialog= findViewById(R.id.imgCloseDialog);
        tvNameWorker.setText(strNameWorker);
//        btnCallWorker.setText(strPhoneWorker);
        //tvRatingPoint.setText(strRatingPoint.toString());
        btnBack=findViewById(R.id.imgBackToMainCustomer);
        tv_PhoneNumber.setText(strPhoneWorker);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickToBackHome();
            }
        });
        //request location permission.
        requestPermision();
        ShowDialogAccept();
        ShowDialogOnComming();
        ShowDialogArrived();
        gotoBillReceipt();
        gotoMainUserWhenCancel();
        //init google map fragment to show map.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("tblWorker");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootDatabaseref.child(strUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.child("image").getValue().toString();
                if(link.equals("")){
                    Glide.with(LocationMapActivity.this).load(snapshot.getValue().toString()).error(R.drawable.avatar_default).into(imgWorkerLocationMap);
                }else{
                    Picasso.get().load(link).into(imgWorkerLocationMap);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LocationMapActivity.this, "Chưa thêm hình ảnh", Toast.LENGTH_SHORT).show();
            }
        });


        btnCallWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCallPhone(Gravity.CENTER);
            }
        });

        btnCancelOrderCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogReasonCustomer(Gravity.CENTER);
            }
        });

    }

    private void showDialogCallPhone(int gravity){
        final Dialog dialog= new Dialog((this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_callphone_customer);

        Window window= dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes= window.getAttributes();
        windowAttributes.gravity= gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        Button btnCallWorkerDialog= dialog.findViewById(R.id.btnCallWorkerDialog);
        Button btnCancelCallWorkerDialog= dialog.findViewById(R.id.btnCancelCallWorkerDialog);

        btnCallWorkerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissionAndCall();
            }
        });
        btnCancelCallWorkerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openDialogReasonCustomer(int gravity){
        final Dialog dialog= new Dialog((this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_reason_customer);

        Window window= dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes= window.getAttributes();
        windowAttributes.gravity= gravity;
        window.setAttributes(windowAttributes);
        Log.e(TAG,"195");

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
        rb_lydokhaccuacustomer=dialog.findViewById(R.id.radio_6);
        EditText edtlydohuycuaCustomer=dialog.findViewById(R.id.edtlydohuycuaCustomer);
        rb_lydokhaccuacustomer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edtlydohuycuaCustomer.setVisibility(View.VISIBLE);
                }
                else{
                    edtlydohuycuaCustomer.setVisibility(View.GONE);
                }
            }
        });
        btnCancelRealOrderOfCustomer = dialog.findViewById(R.id.btnCancelRealOrderOfCustomer);

        btnCancelRealOrderOfCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"207");
                dialog.dismiss();
                CancelOrder();
                CancelOrderCache();
            }
        });

        ImageView imgCloseDialog=dialog.findViewById(R.id.imgCloseDialog);
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void getDataIntent (){
        strPhoneWorker = getIntent().getStringExtra("phoneNumber");
        strNameWorker = getIntent().getStringExtra("full_name");
        // strFee = getIntent().getDoubleExtra("fee",0.0d);
        strRatingPoint= getIntent().getDoubleExtra("ratingPoint",0.0d);
        strDistance= getIntent().getDoubleExtra("distance",0.0d);
        strUID=getIntent().getStringExtra("uID");



    }
    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            locationPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted.
                    locationPermission = true;
                    getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    //to get user location
    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                myLocation=location;
                if(location != null) {
//                    LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                            ltlng, 17f);
//                    mMap.animateCamera(cameraUpdate);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                             LatLng ltlng = new LatLng(10.7695, 106.6825);
                            //LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                    ltlng, 17f);
                            MarkerOptions markerOptions = new MarkerOptions().position(ltlng).title("Vị trí của thợ");
                            googleMap.addMarker(markerOptions).showInfoWindow();
                            mMap.animateCamera(cameraUpdate);
                        }
                    });

                }
            }
        });

        //get destination location when user click on map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                end=latLng;

                mMap.clear();

                start=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                //start route finding
                Findroutes(start,end);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getMyLocation();

    }


    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(LocationMapActivity.this,"Unable to get location",Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyBpOMo6eG-vwEmy-tXNtLAwS83ClCEuIPI")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(LocationMapActivity.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                polyOptions.color(getResources().getColor(R.color.bluesky));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);

            }
            else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start,end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);
    }

    private void clickToBackHome(){

        Intent intent= new Intent(LocationMapActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void goToRating() {
        Intent intent = new Intent(LocationMapActivity.this, RatingActivity.class);
        startActivity(intent);
    }

    private void askPermissionAndCall() {

        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have Call permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSION_REQUEST_CODE_CALL_PHONE
                );
                return;
            }
        }
        this.callNow();
    }

    @SuppressLint("MissingPermission")
    private void callNow() {
        String phoneNumber = this.tv_PhoneNumber.getText().toString();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            this.startActivity(callIntent);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Your call failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    // When you have the request results

    public void onRequestPermissionsResult1(int requestCode,
                                            String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_CALL_PHONE: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    this.callNow();
                }
                // Cancelled or denied.
                else {
                    Log.i( LOG_TAG,"Permission denied!");
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_CALL_PHONE) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void CancelOrder() {
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    Log.e(TAG,order.getUser().getUserID());
                    Log.e(TAG,userAuth.getUid());
                    if(order.getUser().getUserID().equals(userAuth.getUid()) && order.getStatus() == 1){
                        order.setStatus(0);
                        mDatabaseOrder.child(String.valueOf(order.getOrderID())).setValue(order);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void CancelOrderCache() {
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrderCache");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    OrderCache order= postSnapshot.getValue(OrderCache.class);
                    if(order.getUser().getUserID().equals(userAuth.getUid()) && order.getStatus() == 1){
                        order.setStatus(0);
                        mDatabaseOrder.child(String.valueOf(order.getOrderID())).setValue(order);
                        Intent intent= new Intent(LocationMapActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void gotoBillReceipt(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrderCache");
        FirebaseAuth  mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    OrderCache order = postSnapshot.getValue(OrderCache.class);
                    String date= LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 5 && order.getWorker().getWorkerID().equals(strUID) ){
                        Intent intent = new Intent(LocationMapActivity.this,ShowBillForCustomerActivity.class);
                       intent.putExtra("workerId",order.getWorker().getWorkerID());
                        intent.putExtra("date",order.getCreateDate());
                        intent.putExtra("workerName",order.getWorker().getFullName());
                        startActivity(intent);
                        finish();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ShowDialogAccept(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrderCache");
        FirebaseAuth  mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    OrderCache order = postSnapshot.getValue(OrderCache.class);
                    String date= LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 2 && order.getWorker().getWorkerID().equals(strUID) ){
                       // openFeedbackDialog(Gravity.CENTER);
//                        Intent intent = new Intent(LocationMapActivity.this,ShowBillForCustomerActivity.class);
//                        // intent.putExtra("workerId",order.getWorker().getWorkerID());
//                        startActivity(intent);
//                        finishAffinity();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ShowDialogOnComming(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrderCache");
        FirebaseAuth  mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    OrderCache order = postSnapshot.getValue(OrderCache.class);
                    String date= LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 3 && order.getWorker().getWorkerID().equals(strUID) ){
                        //openFeedbackDialog1(Gravity.CENTER);
//                        Intent intent = new Intent(LocationMapActivity.this,ShowBillForCustomerActivity.class);
//                        // intent.putExtra("workerId",order.getWorker().getWorkerID());
//                        startActivity(intent);
//                        finishAffinity();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ShowDialogArrived(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrderCache");
        FirebaseAuth  mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    OrderCache order = postSnapshot.getValue(OrderCache.class);
                    String date= LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 4 && order.getWorker().getWorkerID().equals(strUID) ){
                        //openFeedbackDialog2(Gravity.CENTER);
//                        Intent intent = new Intent(LocationMapActivity.this,ShowBillForCustomerActivity.class);
//                        // intent.putExtra("workerId",order.getWorker().getWorkerID());
//                        startActivity(intent);
//                        finishAffinity();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void gotoMainUserWhenCancel(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrder");
        FirebaseAuth  mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    Order order = postSnapshot.getValue(Order.class);
                    String date= LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 0 ){
                        Intent intent = new Intent(LocationMapActivity.this,MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                        Toast.makeText(LocationMapActivity.this, "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openFeedbackDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dangchuanbi);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winAttributes = window.getAttributes();
        winAttributes.gravity = gravity;
        window.setAttributes(winAttributes);
        if(Gravity.CENTER == gravity){
            dialog.setCancelable(false);
        }
        Button btnOK = dialog.findViewById(R.id.btn_ok_dangchuanbi);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
//    private void openFeedbackDialog1(int gravity){
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_dangdi);
//
//        Window window = dialog.getWindow();
//        if(window == null){
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams winAttributes = window.getAttributes();
//        winAttributes.gravity = gravity;
//        window.setAttributes(winAttributes);
//        if(Gravity.CENTER == gravity){
//            dialog.setCancelable(false);
//        }
//        Button btnOK = dialog.findViewById(R.id.btn_ok_dangdi);
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
    private void openFeedbackDialog2(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dadennoi);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winAttributes = window.getAttributes();
        winAttributes.gravity = gravity;
        window.setAttributes(winAttributes);
        if(Gravity.CENTER == gravity){
            dialog.setCancelable(false);
        }

        Button btnOK = dialog.findViewById(R.id.btn_ok_dadennoi);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}