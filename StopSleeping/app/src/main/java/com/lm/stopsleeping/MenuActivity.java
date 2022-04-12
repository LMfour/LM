package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView my_btn, option_btn;
    private ImageButton back_btn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        my_btn = findViewById(R.id.menu_my_btn);
        option_btn = findViewById(R.id.menu_opt_btn);
        back_btn = findViewById(R.id.menu_back_btn);

        // 마이페이지 프래그먼트가 액티비티에 바로 보이기 위함
        viewMypage();

        // 마이페이지 클릭시 프레그먼트 변환
        my_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMypage();
            }
        });

        // 설정 클릭시 프레그먼트 변환
        option_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                OptionFragment optFragment = new OptionFragment();
                transaction.replace(R.id.frame, optFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // 뒤로가기 클릭시 테스트 페이지 이동
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MenuActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

    }
    private void viewMypage() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MypageFragment myFragment = new MypageFragment();
        transaction.replace(R.id.frame, myFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}