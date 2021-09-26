package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.Database.MyDB;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login, signup;
    MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        login = (Button) findViewById(R.id.btnlogin1);
        signup = (Button) findViewById(R.id.btnsignup1);
        db = new MyDB(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    layout_toast("Xin vui lòng điền hết thông tin",LoginActivity.this);
                }else{
                    Boolean checkuserpass = db.checkUserNamePassword(user,pass);
                    if(checkuserpass == true){
                        layout_toast("Đăng nhập thành công",LoginActivity.this);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else {
                        layout_toast("Đăng nhập không thành công",LoginActivity.this);
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
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