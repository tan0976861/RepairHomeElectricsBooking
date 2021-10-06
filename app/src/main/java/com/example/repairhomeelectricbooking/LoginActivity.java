package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.Database.MyDB;
import com.example.repairhomeelectricbooking.dto.User;
import com.example.repairhomeelectricbooking.dto.Worker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
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
                                            progressDialog.dismiss();
                                            layout_toast("Đăng nhập thành công", LoginActivity.this);
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                            return;
                                        }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    layout_toast("Đăng nhập thành công1213", LoginActivity.this);
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
                                            progressDialog.dismiss();
                                            layout_toast("Đăng nhập thành công", LoginActivity.this);
                                            Intent intent = new Intent(LoginActivity.this, MainWorkerActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                            return;
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