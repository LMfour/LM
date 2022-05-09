package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class TestActivity extends AppCompatActivity {

    private Button btn_start;
    private FloatingActionButton btn_menu;
    private TextView nickName, email;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        nickName = findViewById(R.id.test_name_txt);
        email = findViewById(R.id.test_email_txt);

        btn_start=findViewById(R.id.test_start_btn);
        btn_menu=findViewById(R.id.test_menu_btn);

        updateKakaoLogin();

        // 운전시작 버튼 클릭시 서비스 페이지로 이동
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(TestActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });

        // 메뉴 버튼 클릭시 메뉴 페이지로 이동
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(TestActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    // 로그인이 되어있는지 안되어있는지 확인 후 button 처리
    private void updateKakaoLogin() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    email.setText(user.getKakaoAccount().getEmail());
                } else {
                    nickName.setText(null);
                    email.setText(null);
                }

                return null;
            }
        });
    }



}