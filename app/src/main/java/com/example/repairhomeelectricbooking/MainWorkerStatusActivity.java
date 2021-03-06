package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.OrderCache;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class MainWorkerStatusActivity extends AppCompatActivity {

    private ImageView imgCancelWorker;
    Button btnSuccess,btn_dadendiadiemsuachua,btn_hoanthanh;
    Button btnCancelRealOrder;
    String strUserName,strAddress,strFee;
    DatabaseReference mDatabaseOrder,mDatabaseOrder2;
    RadioButton rb_lydokhac1;
    EditText edtlydohuy1;
    TextView tv_GetOrderBy, tv_locationAddress,tvHangThietBiOrder,tvLoaiThietBiOrder,tv_ThietbiOrder,tv_DescribeProblem,tv_DescribeDeatilProblem,textViewDetail;
    //phone
    private ImageView imgViewPhoneCustomer;
    private TextView tv_callPhone;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    private static final String LOG_TAG = "AndroidExample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_status);
        imgCancelWorker= findViewById(R.id.imgCancelWorker);
        imgViewPhoneCustomer=findViewById(R.id.imgViewPhoneCustomer);
        tv_callPhone=findViewById(R.id.tv_callPhone);
        tv_GetOrderBy=findViewById(R.id.tv_GetOrderBy);
        tv_locationAddress=findViewById(R.id.tv_locationAddress);
        tvHangThietBiOrder=findViewById(R.id.tvHangThietBiOrder);
        tvLoaiThietBiOrder=findViewById(R.id.tvLoaiThietBiOrder);
        tv_ThietbiOrder=findViewById(R.id.tv_ThietbiOrder);
        tv_DescribeProblem=findViewById(R.id.tv_DescribeProblem);
        tv_DescribeDeatilProblem= findViewById(R.id.tv_DescribeDeatilProblem);
        textViewDetail=findViewById(R.id.textViewDetail);
        getDataIntent();
        tv_GetOrderBy.setText(strUserName);
        //tv_locationAddress.setText(strAddress);
       // tv_fee_worker.setText(strFee);
        getOrderPresent();
        gotoMainWorkerWhenCancel();
        btnSuccess=(Button) findViewById(R.id.btnSuccess);
        btn_dadendiadiemsuachua =findViewById(R.id.btn_dadendiadiemsuachua);
        btn_hoanthanh=findViewById(R.id.btn_hoanThanh);

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AcceptOrder();
                AcceptOrderCache();
            }
        });

        imgCancelWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogReasonWorker(Gravity.CENTER);
            }
        });

        imgViewPhoneCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCallPhoneCustomer(Gravity.CENTER);
            }
        });
    }

    private void showCallPhoneCustomer(int gravity){
        final Dialog dialog= new Dialog((this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_callphone_worker);

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

        Button btnCallCustomerDialog= dialog.findViewById(R.id.btnCallCustomerDialog);
        Button btnCancelCallCustomerDialog= dialog.findViewById(R.id.btnCancelCallCustomerDialog);

        btnCallCustomerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissionAndCall();
            }
        });
        btnCancelCallCustomerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getDataIntent()
    {
        strUserName = getIntent().getStringExtra("userName");
        strAddress = getIntent().getStringExtra("address");
        strFee = getIntent().getStringExtra("price");
    }
    private void openDialogReasonWorker(int gravity){
        final Dialog dialog= new Dialog((this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reason_worker);
        Window window= dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes= window.getAttributes();
        windowAttributes.gravity= gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        btnCancelRealOrder = dialog.findViewById(R.id.btnCancelRealOrder);
        rb_lydokhac1=dialog.findViewById(R.id.rb_lydokhac1);
        edtlydohuy1=dialog.findViewById(R.id.edtlydohuy1);
        rb_lydokhac1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edtlydohuy1.setVisibility(View.VISIBLE);
                }
                else{
                    edtlydohuy1.setVisibility(View.GONE);
                }
            }
        });
        btnCancelRealOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                CancelOrder();
                CancelOrderCache();
            }
        });

        ImageView imgCloseDialogWorker=dialog.findViewById(R.id.imgCloseDialogWorker);
        imgCloseDialogWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
        String phoneNumber = this.tv_callPhone.getText().toString();

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
    private void getOrderPresent(){
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus()==1 ){
                        String name=order.getUser().getFullName();
                        String address=order.getUser().getAdress();
                        String fee= order.getFee().toString();
                        String thietbi= order.getProblem();
                        String problem=order.getProblemDetails();
                        String[] info= thietbi.split("-");
                        String phone=order.getUser().getPhone();
                        String phoneReplace= "0" + phone.substring(3,phone.length());
                        tv_GetOrderBy.setText(name);
                        tv_locationAddress.setText(address);
                        tv_ThietbiOrder.setText(info[0]);
                        tvHangThietBiOrder.setText(info[1]);
                        tvLoaiThietBiOrder.setText(info[2]);
                        tv_DescribeProblem.setText(problem);
                        tv_callPhone.setText(phoneReplace);
                        if(problem.contains("-")){
                            String[] detail= problem.split("-");
                            tv_DescribeProblem.setText(detail[0]);
                            if(!detail[1].equals("")){
                                textViewDetail.setVisibility(View.VISIBLE);
                                tv_DescribeDeatilProblem.setVisibility(View.VISIBLE);
                                tv_DescribeDeatilProblem.setText(detail[1]);
                            }
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

    private void AcceptOrder() {
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus() == 1 ){
                        order.setStatus(2);
                        mDatabaseOrder.child(String.valueOf(order.getOrderID())).setValue(order);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void AcceptOrderCache() {
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder2= FirebaseDatabase.getInstance().getReference("tblOrderCache");
        mDatabaseOrder2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    OrderCache order= postSnapshot.getValue(OrderCache.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus() == 1 ){


                        order.setStatus(2);
                        mDatabaseOrder2.child(String.valueOf(order.getOrderID())).setValue(order);
                        Intent intent= new Intent(MainWorkerStatusActivity.this, LocationMapWorkerActivity.class);
                        intent.putExtra("phoneNumber",tv_callPhone.getText().toString());
                        intent.putExtra("distance",order.getWorker().getDistance());
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void CancelOrder() {
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus() == 1 ){
                        order.setStatus(0);
                        mDatabaseOrder.child(String.valueOf(order.getOrderID())).setValue(order);
                        return;
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
        mDatabaseOrder2= FirebaseDatabase.getInstance().getReference("tblOrderCache");
        mDatabaseOrder2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    OrderCache order= postSnapshot.getValue(OrderCache.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus() == 1 ){

//                        intent.putExtra("userName",order.getUser().getFullName());
//                        intent.putExtra("fee",order.getWorker().getFee());
                        order.setStatus(0);
                        mDatabaseOrder2.child(String.valueOf(order.getOrderID())).setValue(order);
                        Intent intent= new Intent(MainWorkerStatusActivity.this, MainWorkerActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void gotoMainWorkerWhenCancel(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrder");
        FirebaseAuth  mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    Order order = postSnapshot.getValue(Order.class);
                    String date= LocalDate.now().toString();
                    if(order.getWorker().getWorkerID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 0 ){
                        Intent intent = new Intent(MainWorkerStatusActivity.this,MainWorkerActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(MainWorkerStatusActivity.this, "????n h??ng ???? b??? h???y", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}