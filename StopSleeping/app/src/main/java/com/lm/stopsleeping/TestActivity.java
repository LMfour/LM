package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TestActivity extends AppCompatActivity {

    private Button btn_start;
    private FloatingActionButton btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn_start=findViewById(R.id.test_start_btn);
        btn_menu=findViewById(R.id.test_menu_btn);

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
}