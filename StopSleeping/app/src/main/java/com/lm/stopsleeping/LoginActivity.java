package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);


        // 회원가입 버튼 클릭시 회원가입 페이지로 이동
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼 클릭시 테스트 페이지로 이동
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });


    }

}