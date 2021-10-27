package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.repairhomeelectricbooking.adapter.BillWorkerAdapter;
import com.example.repairhomeelectricbooking.dto.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowBillForWorkerActivity extends AppCompatActivity {

    Button btnShowBillForWorker,btnSenBill;
    RelativeLayout rlAdditonFee;
    DatabaseReference mDatabaseOrder;
    String[] data = {"1","2","3","4","5"};
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_for_worker);
        btnShowBillForWorker=(Button) findViewById(R.id.btn_themdichvu_worker);
        rlAdditonFee=(RelativeLayout) findViewById(R.id.rl_them_phi);
        btnSenBill=(Button)findViewById(R.id.btnAcceptBillToRating_worker);
        ArrayList<String> list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.billWorkerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BillWorkerAdapter myAdapter = new BillWorkerAdapter(list);
        recyclerView.setAdapter(myAdapter);
        btnShowBillForWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add(data[counter%3]);
                counter++;
                myAdapter.notifyItemInserted(list.size() - 1);
            }
        });
        btnSenBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoneOrder();
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
                    if(order.getWorker().getWorkerID().equals(userAuth.getUid()) && order.getStatus()==1 && order.getUser().getUserID().equals("oK7VSUpZAGUqV12y0BVq7Iyduom2")){

//                        intent.putExtra("userName",order.getUser().getFullName());
//                        intent.putExtra("fee",order.getWorker().getFee());
                        order.setStatus(2);
                        mDatabaseOrder.child(String.valueOf(order.getOrderID())).setValue(order);
                        Intent intent= new Intent(ShowBillForWorkerActivity.this, MainWorkerActivity.class);
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
}