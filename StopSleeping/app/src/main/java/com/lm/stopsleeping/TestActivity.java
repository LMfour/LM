package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {


    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn_start=findViewById(R.id.Test_Camera_Start);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(TestActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });



    }
}