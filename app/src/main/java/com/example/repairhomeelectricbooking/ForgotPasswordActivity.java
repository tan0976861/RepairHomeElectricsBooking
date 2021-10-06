package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {
    public static final String TAG = ForgotPasswordActivity.class.getName();
    private EditText tvForgotPassword,edtEnterOTPForgotPassword;
    private Button btnForgotPassword,btnEnterOTPForgotPassword;
    private LinearLayout layoutEnterOTPForgotPassword;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myTopPostsQuery = database.getReference("tblUser");
    DatabaseReference queryWoker = database.getReference("tblWorker");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
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

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
                    if(user.getPhone().equals(phone)){
                        onClickVerifyPhoneNumber(phone,user.email,user.password);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
        queryWoker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Worker user = postSnapshot.getValue(Worker.class);
                    if(user.getPhone().equals(phone)){
//                        Intent intent= new Intent(ForgotPasswordActivity.this, EnterOTPActivity.class);
//                        startActivity(intent);
                        onClickVerifyPhoneNumber(phone,user.email,user.password);
                        return;
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
    private void onClickVerifyPhoneNumber(String strPhone,String userEmail,String userPassword) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential,userEmail,userPassword);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getApplicationContext(),"Xác thực OTP thất bại",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
                                goToEnterOTPActivity(strPhone,verificationID,userEmail,userPassword);
                            }

                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential,String userEmail,String userPassword) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                            mAuth.signInWithEmailAndPassword(userEmail,userPassword)
                                    .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                goToMainUserActivity(userEmail);

                                                //System.out.println("Luongdeptrai");
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
//                            FirebaseUser user = task.getResult().getUser();
//                            // Update UI
//                            goToMainUserActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),"Mã OTP của bạn không hợp lệ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }
    private void goToEnterOTPActivity(String strPhone, String verificationID,String userEmail,String userPassword) {
        Intent intent = new Intent(ForgotPasswordActivity.this,EnterOTPNewPasswordActivity.class);
        intent.putExtra("phone_number",strPhone);
        intent.putExtra("verificationID",verificationID);
        intent.putExtra("userEmail",userEmail);
        intent.putExtra("userPassword",userPassword);
        startActivity(intent);
        finishAffinity();
        finish();
    }

    private void goToMainUserActivity(String phoneNumber) {
        Intent intent = new Intent(this,ResetPasswordActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
        finish();
    }

//    private void onClickSendOTPCode(String strOTP) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(strVerificationID, strOTP);
//        signInWithPhoneAuthCredential(credential);
//    }
}