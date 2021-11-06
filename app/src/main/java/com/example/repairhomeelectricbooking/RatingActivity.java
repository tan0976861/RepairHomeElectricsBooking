package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.OrderCache;
import com.example.repairhomeelectricbooking.dto.Rating;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class RatingActivity extends AppCompatActivity {

    Button btnGui;
    int strOrderId;
    EditText tv_ratingCommentWorker;
    TextView tv_ratingNameWorker;
    String strWorkerId,strDateOrder,strCustomerComment,strWorkerName;
    RatingBar rtRatingBarStart;
    double ratingPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        btnGui=(Button) findViewById(R.id.btnSendRating);
        rtRatingBarStart=(RatingBar) findViewById(R.id.rtRatingBarStart);
        tv_ratingCommentWorker=(EditText)  findViewById(R.id.tv_ratingCommentWorker);
        tv_ratingNameWorker=(TextView) findViewById(R.id.tv_ratingNameWorker);
        getDataIntent();
        tv_ratingNameWorker.setText(strWorkerName);
        rtRatingBarStart.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
               // RatingBar rb= (RatingBar) view ;
                //ratingPoint=(double) rb.getRating();
                ratingPoint=(double )rtRatingBarStart.getRating();
                System.out.println("testRating Point: " + ratingPoint);
            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReferenceRating= FirebaseDatabase.getInstance().getReference().child("tblRating");
                strCustomerComment=tv_ratingCommentWorker.getText().toString();
                FirebaseAuth mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
                Rating rating = new Rating(mAuth.getUid(),strWorkerId,strCustomerComment,strDateOrder,ratingPoint);
                databaseReferenceRating.child("" + strOrderId).setValue(rating);
                openFeedbackDialog(Gravity.CENTER);
            }
        });




    }
    private void getDataIntent() {
        strOrderId = getIntent().getIntExtra("orderId",0);
        strWorkerId=getIntent().getStringExtra("workerId");
        strDateOrder=getIntent().getStringExtra("date");
        strWorkerName=getIntent().getStringExtra("workerName");


    }
    private void openFeedbackDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating_success);

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

        Button btnOK = dialog.findViewById(R.id.btn_ok_rating_success);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteOrderCache();
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
    private void DeleteOrderCache(){
        DatabaseReference mDatabaseOrder = FirebaseDatabase.getInstance().getReference("tblOrderCache");
        FirebaseAuth mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        mDatabaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    Order order = postSnapshot.getValue(Order.class);
                    String date= LocalDate.now().toString();
                    if(order.getUser().getUserID().equals(mAuth.getCurrentUser().getUid()) && order.getCreateDate().equals(date) && order.getStatus() == 5 && order.getWorker().getWorkerID().equals("pI8Mffqvcsfa1HkRuTtKoy4Li2c2") ){
                        mDatabaseOrder.child(String.valueOf(order.getOrderID())).removeValue();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}