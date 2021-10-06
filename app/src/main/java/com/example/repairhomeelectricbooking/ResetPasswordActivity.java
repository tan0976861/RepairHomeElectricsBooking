package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText edtEnterNewPassword, edtReEnterNewPassword;
    private Button btnReNewPasswordToMain;
    private String uId,strNewPassword,strEmail,strPassword,strPhone;
    FirebaseDatabase database;
    DatabaseReference mDatabase,mDatabase2;
    //Query allPost = FirebaseDatabase.getInstance().getReference().child("tblUser");
    DatabaseReference queryUser, queryWorker;
    public static final String TAG = ResetPasswordActivity.class.getName();

    //Query allPost = FirebaseDatabase.getInstance().getReference().child("Posts");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        edtEnterNewPassword = (EditText) findViewById(R.id.edt_enter_new_password);
//        edtReEnterNewPassword = (EditText) findViewById(R.id.edt_re_enter_new_password);
        btnReNewPasswordToMain = (Button) findViewById(R.id.btn_re_new_password_to_main);
        getDataIntent();
        String s= strPhone.substring(0,3) + "0" + strPhone.substring(3,strPhone.length());
        Log.d(TAG, strPhone);
        Log.d(TAG, s);
        mDatabase = FirebaseDatabase.getInstance().getReference("tblUser");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("tblWorker");

        btnReNewPasswordToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNewPassword = edtEnterNewPassword.getText().toString();
                onClickSignIn(strNewPassword,s);
            }
        });

    }


    private void getDataIntent (){
        strPhone = getIntent().getStringExtra("phone_number");
//        strPhone = "+840967340455";
    }

    private void onClickSignIn(String newPassword,String phone){
        mDatabase = FirebaseDatabase.getInstance().getReference("tblUser");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
                    if(user.getPhone().equals(phone)){
                        goToMainUser(newPassword,user.getEmail(),user.getPassword());
                        FirebaseAuth.getInstance().signOut();
                        Log.d(TAG, "83");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
        mDatabase2 = FirebaseDatabase.getInstance().getReference("tblWorker");
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Worker user = postSnapshot.getValue(Worker.class);
                    if(user.getPhone().equals(phone)){
                        goToMainWorker(newPassword,user.getEmail(),user.password);
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser firebaseUser  = mAuth.getCurrentUser();
//                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tblUser");
//                            mDatabase.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    // TODO: handle the post
//                                    if(dataSnapshot.child(firebaseUser.getUid()).exists()){
//
//                                        firebaseUser.updatePassword(newPassword)
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            queryUser.child(firebaseUser.getUid()).child("password").setValue(newPassword);
//                                                            Toast.makeText(ResetPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
//                                                                    Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
//                                                            startActivity(intent);
//
//                                                        }
//                                                    }
//                                                });
//                                    }
//                                }
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    layout_toast("Đăng nhập thành công1213", ResetPasswordActivity.this);
//                                    // Getting Post failed, log a message
//                                    // ...
//                                }
//                            });
//                            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("tblWorker");
//                            mDatabase2.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    // TODO: handle the post
//                                    if(dataSnapshot.child(firebaseUser.getUid()).exists()){
//                                        firebaseUser.updatePassword(newPassword)
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            queryWorker.child(firebaseUser.getUid()).child("password").setValue(newPassword);
//                                                            Toast.makeText(ResetPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
//                                                                    Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(ResetPasswordActivity.this, MainWorkerActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    }
//                                                });
//                                    }
//                                }
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    // Getting Post failed, log a message
//                                    // ...
//                                }
//                            });
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(ResetPasswordActivity.this, "Tài khoản hoặc mật khẩu không chính xác.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

//    private void reNewPassword(String newPassword) {
////        database = FirebaseDatabase.getInstance();
//        queryUser = FirebaseDatabase.getInstance().getReference("tblUser");
//        queryWorker = FirebaseDatabase.getInstance().getReference("tblWorker");
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        queryUser.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // TODO: handle the post
//                if(dataSnapshot.child(user.getUid()).exists()){
//                    user.updatePassword(newPassword)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        queryUser.child(user.getUid()).child("password").setValue(newPassword);
//                                        Toast.makeText(ResetPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
//                                                Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
//                                        startActivity(intent);
//
//                                    }
//                                }
//                            });
//
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                // ...
//            }
//        });
//        queryWorker.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot2) {
//                // TODO: handle the post
//                if(dataSnapshot2.child(user.getUid()).exists()){
//                    user.updatePassword(newPassword)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        queryWorker.child(user.getUid()).child("password").setValue(newPassword);
//                                        Toast.makeText(ResetPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
//                                                Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(ResetPasswordActivity.this, MainWorkerActivity.class);
//                                        startActivity(intent);
//                                    }
//                                }
//                            });
//
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                // ...
//            }
//        });
//
//    }


    private void goToMainUser(String newPassword,String email,String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "258");
                            FirebaseUser firebaseUser  = mAuth.getCurrentUser();
                            firebaseUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "264");
                                                DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("tblUser");
//                                                FirebaseUser firebaseUser2  = FirebaseAuth.getInstance().getCurrentUser();
                                                mDatabase3.child(firebaseUser.getUid()).child("password").setValue(newPassword);
                                                Toast.makeText(ResetPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                        }else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ResetPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goToMainWorker(String  newPassword,String email,String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser  = mAuth.getCurrentUser();
                            Log.d(TAG, "294");
                            if(firebaseUser == null){
                                return;
                            }else{
//                                DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("tblWorker");
//                                mDatabase3.child(firebaseUser.getUid()).child("password").setValue(newPassword);
                                firebaseUser.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "302");
                                                    Toast.makeText(ResetPasswordActivity.this, "Cập Nhật Mật Khẩu Thành Công.",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ResetPasswordActivity.this, MainWorkerActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                                DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("tblWorker");
                                mDatabase3.child(firebaseUser.getUid()).child("password").setValue(newPassword);
                                Intent intent = new Intent(ResetPasswordActivity.this, MainWorkerActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ResetPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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