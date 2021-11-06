package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ShowBillForCustomerActivity extends AppCompatActivity {
    Button btnAcceptBillToRating;
    String strWorkerId,dateOrder,strWorkerName;
    int strOrderId;
    DatabaseReference mDatabaseOrder;
    TextView tv_ngaythanghoadon,tv_giaPhiPhuTung,tv_giaPhiVeSinh,tv_giaTienCong,tv_TotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_for_customer);
        btnAcceptBillToRating = (Button) findViewById(R.id.btnAcceptBillToRating);
        tv_ngaythanghoadon=(TextView) findViewById(R.id.tv_ngaythanghoadon);
        tv_giaPhiPhuTung=(TextView) findViewById(R.id.tv_giaPhiPhuTung);
        tv_giaPhiVeSinh=(TextView) findViewById(R.id.tv_giaPhiVeSinh);
        tv_giaTienCong=(TextView) findViewById(R.id.tv_giaTienCong);
        tv_TotalCost=(TextView) findViewById(R.id.tv_TotalCost);

        getDataIntent();
        BillReceiptPresent();
        btnAcceptBillToRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTotalFeeOnOrder();
                Intent intent = new Intent(ShowBillForCustomerActivity.this, RatingActivity.class);
                intent.putExtra("orderId",strOrderId);
                intent.putExtra("date",dateOrder);
                intent.putExtra("workerId",strWorkerId);
                intent.putExtra("workerName",strWorkerName);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDataIntent() {
        strWorkerId = getIntent().getStringExtra("workerId");
        dateOrder = getIntent().getStringExtra("date");
        strWorkerName=getIntent().getStringExtra("workerName");
    }

    private void BillReceiptPresent() {
        DatabaseReference mDatabaseOrder2 = FirebaseDatabase.getInstance().getReference("tblOrderCache");
        FirebaseAuth mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // TODO: handle the post
                    OrderCache order = postSnapshot.getValue(OrderCache.class);
                    String date = LocalDate.now().toString();
                    if (order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 5 && order.getWorker().getWorkerID().equals(strWorkerId)) {
                        strOrderId =(int)order.getOrderID();
                        tv_ngaythanghoadon.setText(order.getCreateDate());
                        double phiPhuTung=order.getListService().get(0).getFee();
                        double phiDichuyen=order.getListService().get(1).getFee();
                        double phiDichVu=order.getListService().get(2).getFee();
                        double totalCost=phiPhuTung + phiDichuyen + phiDichVu;
                        tv_giaPhiPhuTung.setText(Double.toString(phiPhuTung));
                        tv_giaPhiVeSinh.setText(Double.toString(phiDichuyen));
                        tv_giaTienCong.setText(Double.toString(phiDichVu));
                        tv_TotalCost.setText(Double.toString(totalCost));
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void UpdateTotalFeeOnOrder() {
        FirebaseAuth userAuth= (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    String date = LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(userAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 5 && order.getWorker().getWorkerID().equals(strWorkerId)){
                       double totalCost=Double.parseDouble(tv_TotalCost.getText().toString());
                        System.out.println("TotalCost:102" +totalCost);
                        System.out.println("StringOderID" + strOrderId);
                        mDatabaseOrder.child("" + strOrderId).child("fee").setValue(totalCost);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}