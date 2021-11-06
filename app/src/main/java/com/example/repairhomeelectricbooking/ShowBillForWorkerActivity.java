package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.repairhomeelectricbooking.adapter.BillWorkerAdapter;
import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShowBillForWorkerActivity extends AppCompatActivity {

    Button btnShowBillForWorker,btnSenBill;
    RelativeLayout rlAdditonFee;
    TextView tv_TotalCost_worker,tv_ngaythanghoadon_worker;
    EditText tv_giaTienCong_worker,edt_giaPhiDiChuyen_worker,edt_giaPhiPhuTung_worker;
    DatabaseReference mDatabaseOrder,mDatabaseOrder2;
    String[] data = {"1","2","3","4","5"};
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_for_worker);
        rlAdditonFee=(RelativeLayout) findViewById(R.id.rl_them_phi);
        btnSenBill=(Button)findViewById(R.id.btnAcceptBillToRating_worker);
        edt_giaPhiPhuTung_worker=(EditText) findViewById(R.id.edt_giaPhiPhuTung_worker);
        edt_giaPhiDiChuyen_worker=(EditText) findViewById(R.id.edt_giaPhiDiChuyen_worker);
        tv_giaTienCong_worker=(EditText) findViewById(R.id.tv_giaTienCong_worker);
       // tv_TotalCost_worker=(TextView) findViewById(R.id.tv_TotalCost_worker);
        tv_ngaythanghoadon_worker= (TextView) findViewById(R.id.tv_ngaythanghoadon_worker);
        String date= LocalDate.now().toString();
        tv_ngaythanghoadon_worker.setText(date);
        ArrayList<String> list = new ArrayList<>();
        btnSenBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoneOrder();
                DoneOrderCache();
            }
        });
    }
    private void DoneOrder(){
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder= FirebaseDatabase.getInstance().getReference("tblOrder");
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus()==2 && order.getUser().getUserID().equals("oK7VSUpZAGUqV12y0BVq7Iyduom2")){

//                        intent.putExtra("userName",order.getUser().getFullName());
//                        intent.putExtra("fee",order.getWorker().getFee());
                        Service service1= new Service("Phí phụ tùng:",Double.parseDouble(edt_giaPhiPhuTung_worker.getText().toString()));
                        Service service2= new Service("Phí di chuyển:",Double.parseDouble(edt_giaPhiDiChuyen_worker.getText().toString()));
                        Service service3= new Service("Phí dịch vụ:",Double.parseDouble(tv_giaTienCong_worker.getText().toString()));
                        //double totalFee= edt_giaPhiDiChuyen_worker.getText() + edt_giaPhiPhuTung_worker.getText() + tv_giaTienCong_worker;
                        List<Service> list= new ArrayList<>();
                        list.add( service1);
                        list.add( service2);
                        list.add( service3);
                        order.setListService(list);
                        order.setStatus(5);
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
    private void DoneOrderCache(){
        FirebaseUser userAuth= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseOrder2= FirebaseDatabase.getInstance().getReference("tblOrderCache");
        mDatabaseOrder2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Order order= postSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus()==4 && order.getUser().getUserID().equals("oK7VSUpZAGUqV12y0BVq7Iyduom2")){

//                        intent.putExtra("userName",order.getUser().getFullName());
//                        intent.putExtra("fee",order.getWorker().getFee());
                        Service service1= new Service("Phí phụ tùng:",Double.parseDouble(edt_giaPhiPhuTung_worker.getText().toString()));
                        Service service2= new Service("Phí di chuyển:",Double.parseDouble(edt_giaPhiDiChuyen_worker.getText().toString()));
                        Service service3= new Service("Phí dịch vụ:",Double.parseDouble(tv_giaTienCong_worker.getText().toString()));
                        //double totalFee= edt_giaPhiDiChuyen_worker.getText() + edt_giaPhiPhuTung_worker.getText() + tv_giaTienCong_worker;
                        List<Service> list= new ArrayList<>();
                        list.add( service1);
                        list.add( service2);
                        list.add( service3);
                        order.setListService(list);
                        order.setStatus(5);
                        mDatabaseOrder2.child(String.valueOf(order.getOrderID())).setValue(order);
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
}