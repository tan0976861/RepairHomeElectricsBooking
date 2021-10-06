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

import com.example.repairhomeelectricbooking.dto.User;
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

public class EnterOTPNewPasswordActivity extends AppCompatActivity {

    public static final String TAG = EnterOTPNewPasswordActivity.class.getName();
    private FirebaseAuth mAuth;
    EditText edtOTPToNewPassword;
    Button btnSentOTPToNewPassword;
    TextView tvResentOPTToNewPassword;
    String strPhoneNumber,strVerificationID,strUserEmail,strUserPassword;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpnew_password);

        edtOTPToNewPassword = (EditText) findViewById(R.id.edtOTPtoReNewPassword);
        btnSentOTPToNewPassword = (Button) findViewById(R.id.btnSentOTPtoReNewPassword);
        tvResentOPTToNewPassword = (TextView) findViewById(R.id.tvResentOPTtoReNewPassword) ;
        getDataIntent();
        mAuth = (FirebaseAuth) FirebaseAuth.getInstance();

        btnSentOTPToNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strOTP = edtOTPToNewPassword.getText().toString().trim();
                onClickSendOTPCode(strOTP,strUserEmail,strUserPassword);
            }
        });

        tvResentOPTToNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSendOTPAgain(strUserEmail,strUserPassword);
            }

        });
    }

    private void getDataIntent (){
        strPhoneNumber = getIntent().getStringExtra("phone_number");
        strVerificationID = getIntent().getStringExtra("verificationID");
        strUserEmail = getIntent().getStringExtra("userEmail");
        strUserPassword = getIntent().getStringExtra("userPassword");
    }


    private void onClickSendOTPCode(String strOTP,String strUserEmail,String strUserPassword) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(strVerificationID, strOTP);
        signInWithPhoneAuthCredential(credential,strUserEmail,strUserPassword);
    }

    private void onClickSendOTPAgain(String userEmail,String userPassword) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential,userEmail,userPassword);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                layout_toast("Xác thực OTP thất bại",EnterOTPNewPasswordActivity.this);
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, String userEmail,String userPassword) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            goToResetPasswordActivity(userEmail,userPassword);
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(EnterOTPNewPasswordActivity.this,user.getPhoneNumber().toString(),Toast.LENGTH_SHORT).show();
                            // Update UI
                            goToResetPasswordActivity(user.getPhoneNumber());
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(EnterOTPNewPasswordActivity.this,"Mã OTP của bạn không hợp lệ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }

    private void goToResetPasswordActivity(String phone) {
        Intent intent = new Intent(this,ResetPasswordActivity.class);
        intent.putExtra("phone_number",phone);
//        intent.putExtra("userPassword",userPassword);
        startActivity(intent);
        finish();
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