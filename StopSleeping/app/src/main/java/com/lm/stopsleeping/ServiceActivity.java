package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ServiceActivity extends AppCompatActivity {

    private Button btn_stop;
    private FloatingActionButton btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btn_stop=findViewById(R.id.svc_stop_btn);
        btn_menu=findViewById(R.id.svc_menu_btn);

        // 운전중지 버튼 클릭시 테스트 페이지로 이동
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ServiceActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        // 메뉴 버튼 클릭시 메뉴 페이지로 이동
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ServiceActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}