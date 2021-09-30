package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeRegisterActivity extends AppCompatActivity {
    Button btnGoToSignUpUser,btnGoToSignUpWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_register);
        btnGoToSignUpUser = (Button) findViewById(R.id.btn_goto_signup_user);
        btnGoToSignUpWorker = (Button) findViewById(R.id.btn_goto_signup_worker);
        btnGoToSignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeRegisterActivity.this,DangKyActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        btnGoToSignUpWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeRegisterActivity.this,DangKyWorkerActiviry.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}