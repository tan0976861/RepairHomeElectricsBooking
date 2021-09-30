package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.dto.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText tvForgotPassword;
    private Button btnForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        tvForgotPassword = (EditText) findViewById(R.id.tv_forgot_password);
        btnForgotPassword = (Button) findViewById(R.id.btn_forgot_password);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTvForgotPassword = tvForgotPassword.getText().toString();
                checkPhoneExist(strTvForgotPassword);
            }
        });
    }

    public void checkPhoneExist(String phone){
        // My top posts by number of stars
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Query allPost = FirebaseDatabase.getInstance().getReference().child("tblUser");
       DatabaseReference myTopPostsQuery = database.getReference("tblUser");
        //Query allPost = FirebaseDatabase.getInstance().getReference().child("Posts");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
                    if(user.getPhone().equals(phone)){
//                        Intent intent= new Intent(ForgotPasswordActivity.this, EnterOTPActivity.class);
//                        startActivity(intent);
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                                .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                            user1.updatePassword("123456789")
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(ForgotPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                            myTopPostsQuery.child(user1.getUid()).child("password").setValue("123456789");
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(ForgotPasswordActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
    }

}