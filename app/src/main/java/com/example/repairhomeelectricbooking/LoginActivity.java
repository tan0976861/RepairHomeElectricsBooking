package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.Database.MyDB;
import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.example.repairhomeelectricbooking.fcm.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    TextView tvQuenMatKhau;
    Button login;
    LinearLayout layoutSignUp;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        tvQuenMatKhau = (TextView) findViewById(R.id.tv_quenmatkhau);
        login = (Button) findViewById(R.id.btnlogin1);
        layoutSignUp = (LinearLayout) findViewById(R.id.layout_sign_up);
        progressDialog = new ProgressDialog(this);
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                if(strEmail.equals("") || strPassword.equals("")){
                    layout_toast("Xin vui lòng điền hết thông tin",LoginActivity.this);
                }else{
                    onCickSignIn(strEmail,strPassword);
                }
            }
        });

        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangeRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onCickSignIn(String email,String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser  = mAuth.getCurrentUser();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tblUser");
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // TODO: handle the post
                                    if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                            // TODO: handle the post
                                            User user = postSnapshot.getValue(User.class);
                                            if(firebaseUser.getUid().equals(user.userID)){
                                                if(user.getStatus() == 1){
                                                    progressDialog.dismiss();
                                                    FirebaseMessaging.getInstance().getToken()
                                                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<String> task) {
                                                                    if (!task.isSuccessful()) {
                                                                        return;
                                                                    }

                                                                    // Get new FCM registration token
                                                                    String token = task.getResult();
                                                                    FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
                                                                    layout_toast("Đăng nhập thành công", LoginActivity.this);
                                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finishAffinity();
                                                                    finish();
                                                                }
                                                            });
                                                }else{
                                                    progressDialog.dismiss();
                                                    openDialog(Gravity.CENTER,"Tài khoản của bạn đã bị khóa");
                                                }
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    layout_toast("Đăng nhập không thành công", LoginActivity.this);
                                    // Getting Post failed, log a message
                                    // ...
                                }
                            });
                            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("tblWorker");
                            mDatabase2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // TODO: handle the post
                                    if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                            // TODO: handle the post
                                            Worker worker = postSnapshot.getValue(Worker.class);
                                            if(firebaseUser.getUid().equals(worker.workerID)){
                                                if(worker.getStatus() == 1){
                                                    progressDialog.dismiss();
                                                    FirebaseMessaging.getInstance().getToken()
                                                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<String> task) {
                                                                    if (!task.isSuccessful()) {
                                                                        return;
                                                                    }

                                                                    // Get new FCM registration token
                                                                    String token = task.getResult();

                                                                    FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
                                                                    layout_toast("Đăng nhập thành công", LoginActivity.this);
                                                                    Intent intent = new Intent(LoginActivity.this, MainWorkerActivity.class);
                                                                    startActivity(intent);
                                                                    finishAffinity();
                                                                    finish();
                                                                }
                                                            });
                                                }else if(worker.getStatus() == 0){
                                                    progressDialog.dismiss();
                                                    openDialog(Gravity.CENTER,"Tài khoản của bạn đã bị khóa");
                                                }else{
                                                    progressDialog.dismiss();
                                                    openDialog(Gravity.CENTER,"Tài khoản của bạn đang được ADMIN xét duyệt vui lòng đợi trong 30 phút");
                                                }
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Getting Post failed, log a message
                                    // ...
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void openDialog(int gravity,String title){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dangdi);
        TextView tv = dialog.findViewById(R.id.tv_dangdi_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        tv.setText(title);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winAttributes = window.getAttributes();
        winAttributes.gravity = gravity;
        window.setAttributes(winAttributes);
        if(Gravity.CENTER == gravity){
            dialog.setCancelable(false);
        }

        Button btnOK = dialog.findViewById(R.id.btn_ok_dangdi);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void layout_toast(String text,Context context){
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