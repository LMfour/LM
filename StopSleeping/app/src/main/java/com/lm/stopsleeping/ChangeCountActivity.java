package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeCountActivity extends AppCompatActivity {

    public static final String TAG = "ChangeCountActivity";
    ImageButton btn_back;
    Button count_sellect_btn1;
    Button count_sellect_btn2;
    Button count_sellect_btn3;


    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_count);

        mTextView = (TextView) findViewById(R.id.textView_count_answer);
        count_sellect_btn1 = (Button) findViewById(R.id.count_sellect_btn1);
        count_sellect_btn2 = (Button) findViewById(R.id.count_sellect_btn2);
        count_sellect_btn3 = (Button) findViewById(R.id.count_sellect_btn3);

        count_sellect_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "1단계");
                mTextView.setText("1단계");
                mTextView.setTextSize(20);
                mTextView.setTextColor(Color.GRAY);
            }
        });

        count_sellect_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "2단계");
                mTextView.setText("2단계");
                mTextView.setTextSize(20);
                mTextView.setTextColor(Color.GRAY);
            }
        });
        count_sellect_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "3단계");
                mTextView.setText("3단계");
                mTextView.setTextSize(20);
                mTextView.setTextColor(Color.GRAY);
            }
        });


        btn_back = findViewById(R.id.men_back_btn);

        // 뒤로가기 버튼 클릭시 메뉴 페이지로 이동
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ChangeCountActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

}