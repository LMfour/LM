package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChangeCountActivity extends AppCompatActivity {

    public static final String TAG = "ChangeCountActivity";
    ImageButton btn_back;
    Button count_sellect_btn1;
    Button count_sellect_btn2;
    Button count_sellect_btn3;

    TextView textview_result_1;
    TextView textview_result_2;
    TextView textview_result_3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_count);


        count_sellect_btn1 = (Button) findViewById(R.id.count_sellect_btn1);
        count_sellect_btn2 = (Button) findViewById(R.id.count_sellect_btn2);
        count_sellect_btn3 = (Button) findViewById(R.id.count_sellect_btn3);


        textview_result_1 = (TextView) findViewById(R.id.alarm_count_txt);
        textview_result_2 = (TextView) findViewById(R.id.sing_count_txt);
        textview_result_3 = (TextView) findViewById(R.id.station_count_txt);


        count_sellect_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_1();
            }
        });

        count_sellect_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_2();
            }
        });


        count_sellect_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_3();
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

    void show_1()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("알람");
        ListItems.add("노래");
        ListItems.add("휴게소 안내");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //    builder.setTitle("선택");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                // Toast.makeText(ChangeAlarmActivity.this, selectedText, Toast.LENGTH_LONG).show();
                textview_result_1.setText(selectedText);
            }
        });
        builder.show();
    }


    void show_2()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("알람");
        ListItems.add("노래");
        ListItems.add("휴게소 안내");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //    builder.setTitle("선택");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                // Toast.makeText(ChangeAlarmActivity.this, selectedText, Toast.LENGTH_LONG).show();
                textview_result_2.setText(selectedText);
            }
        });
        builder.show();
    }

    void show_3()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("알람");
        ListItems.add("노래");
        ListItems.add("휴게소 안내");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //    builder.setTitle("선택");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                // Toast.makeText(ChangeAlarmActivity.this, selectedText, Toast.LENGTH_LONG).show();
                textview_result_3.setText(selectedText);
            }
        });
        builder.show();
    }


}