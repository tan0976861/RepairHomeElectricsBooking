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

public class EnterOTPWorkerActivity extends AppCompatActivity {
    public static final String TAG = EnterOTPActivity.class.getName();
    private FirebaseAuth mAuth;
    EditText edtOTPWorker;
    Button btnSentOTPWorker;
    TextView tvResentOPTWorker;
    String strPhoneNumber,strVerificationID;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpworker);

        edtOTPWorker = (EditText) findViewById(R.id.edtOTP_worker);
        btnSentOTPWorker = (Button) findViewById(R.id.btnSentOTP_worker);
        tvResentOPTWorker = (TextView) findViewById(R.id.tvResentOPT_worker) ;
        getDataIntent();
        mAuth = (FirebaseAuth) FirebaseAuth.getInstance();
        btnSentOTPWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strOTP = edtOTPWorker.getText().toString().trim();
                onClickSendOTPCode(strOTP);
            }

        });

        tvResentOPTWorker.setOnClickListener(new View.OnClickListener() {
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
                                layout_toast("Xác thực OTP thất bại",EnterOTPWorkerActivity.this);
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

                            FirebaseUser worker = task.getResult().getUser();
                            // Update UI
                            goToMainWokerActivity(worker.getPhoneNumber());
                            layout_toast("Đăng ký thành công",EnterOTPWorkerActivity.this);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                layout_toast("Mã OTP của bạn không hợp lệ",EnterOTPWorkerActivity.this);
                            }
                        }
                    }

                });
    }

    private void goToMainWokerActivity(String phoneNumber) {
        Intent intent = new Intent(this,MainWorkerActivity.class);
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