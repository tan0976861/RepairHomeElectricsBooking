package com.example.repairhomeelectricsbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.repairhomeelectricsbooking.Database.MyDB;
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

import java.util.concurrent.TimeUnit;

public class DangKyActivity extends AppCompatActivity {
    public static final String TAG = DangKyActivity.class.getName();
    EditText username,password,repassword,phonenumber;
    Button signup,signin;
    MyDB db;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        phonenumber = (EditText) findViewById(R.id.phone);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
        db = new MyDB(this);
        mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String strPhone = phonenumber.getText().toString().trim();
                onClickVerifyPhoneNumber(strPhone);
//                Intent intent = new Intent(getApplicationContext(), EnterOTPActivity.class);
//                startActivity(intent);


//                if(user.equals("") || pass.equals("") || repass.equals("")){
//                    Toast.makeText(DangKyActivity.this,"Xin vui lòng điền hết thông tin",Toast.LENGTH_SHORT).show();
//                }else{
//                    if(pass.equals(repass)){
//                        Boolean checkuser = db.checkUserName(user);
//                        if(checkuser == false){
//                            Boolean insert = db.insertData(user,pass);
//                            if(insert == true){
//                                onClickVerifyPhoneNumber(strPhone);
//                            }else {
//                                Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(DangKyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }


        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickVerifyPhoneNumber(String strPhone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(DangKyActivity.this,"Xác thực OTP thất bại",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
                                goToEnterOTPActivity(strPhone,verificationID);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToMainUserActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(DangKyActivity.this,"Mã OTP của bạn không hợp lệ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }

    private void goToMainUserActivity(String phoneNumber) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("phonenumber",phoneNumber);
        startActivity(intent);
    }

    private void goToEnterOTPActivity(String strPhone, String verificationID) {
        Intent intent = new Intent(this,EnterOTPActivity.class);
        intent.putExtra("phonenumber",strPhone);
        intent.putExtra("verificationID",verificationID);
        startActivity(intent);
    }

}