package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ChangeSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_song);
        ImageButton btn_back;



        btn_back = findViewById(R.id.men_back_btn);

        // 뒤로가기 버튼 클릭시 전 페이지로 이동
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ChangeSongActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
    }
}