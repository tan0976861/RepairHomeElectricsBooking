package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class EnterOTPActivity extends AppCompatActivity {
    public static final String TAG = EnterOTPActivity.class.getName();
    private FirebaseAuth mAuth;
    EditText edtOTP;
    Button btnSentOTP;
    TextView tvResentOPT;
    String strPhoneNumber,strVerificationID;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpactivity);

        edtOTP = (EditText) findViewById(R.id.edtOTP);
        btnSentOTP = (Button) findViewById(R.id.btnSentOTP);
        tvResentOPT = (TextView) findViewById(R.id.tvResentOPT) ;
        getDataIntent();
        mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        btnSentOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strOTP = edtOTP.getText().toString().trim();
                onClickSendOTPCode(strOTP);
            }

        });

        tvResentOPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSendOTPAgain();
            }

        });
    }

    private void getDataIntent (){
        strPhoneNumber = getIntent().getStringExtra("phonenumber");
        strVerificationID = getIntent().getStringExtra("verificationID");
    }


    private void onClickSendOTPCode(String strOTP) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(strVerificationID, strOTP);
        signInWithPhoneAuthCredential(credential);
    }

    private void onClickSendOTPAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                layout_toast("X??c th???c OTP th???t b???i",EnterOTPActivity.this);
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
                                strVerificationID = verificationID;
                                mForceResendingToken = forceResendingToken;
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
                            layout_toast("????ng k?? th??nh c??ng",EnterOTPActivity.this);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                layout_toast("M?? OTP c???a b???n kh??ng h???p l???",EnterOTPActivity.this);
                            }
                        }
                    }

                });
    }

    private void goToMainUserActivity(String phoneNumber) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("phonenumber",phoneNumber);
        startActivity(intent);
        finishAffinity();
    }

    private void layout_toast(String text, Context context){
        Toast toast = new Toast(context);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_toast,(ViewGroup) findViewById(R.id.layout_custom_toast));
        TextView tvToast = view.findViewById(R.id.tv_Toast);
        tvToast.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}