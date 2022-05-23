package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CheckActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private DBHelper mDBHelper;
    private ArrayList<DateItem> recordItems = new ArrayList<>();
    private CustomAdapter mAdapter;
    private RecyclerView mRV_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        setInit();
        btn_back = findViewById(R.id.men_back_btn);

        // 뒤로가기 버튼 클릭시 메뉴 페이지로 이동
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(CheckActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setInit() {
        mDBHelper = new DBHelper(this);
        recordItems = mDBHelper.getRecordList();
        mRV_date = findViewById(R.id.rv_date);

        if(mAdapter  == null){
            mAdapter = new CustomAdapter(recordItems, this);
            mRV_date.setHasFixedSize(true);
            mRV_date.setAdapter(mAdapter);
        }
    }
}