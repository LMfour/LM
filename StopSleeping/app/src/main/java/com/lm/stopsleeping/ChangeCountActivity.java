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
    private DBHelper mDBHelper;
    private TextView firstFunc;
    private TextView secondFunc;
    private TextView thirdFunc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_count);


        count_sellect_btn1 = (Button) findViewById(R.id.count_sellect_btn1);
        count_sellect_btn2 = (Button) findViewById(R.id.count_sellect_btn2);
        count_sellect_btn3 = (Button) findViewById(R.id.count_sellect_btn3);
        mDBHelper = new DBHelper(this);

        firstFunc = findViewById(R.id.first_func);
        secondFunc = findViewById(R.id.second_func);
        thirdFunc = findViewById(R.id.thrid_func);

        viewFirst();
        viewSecond();
        viewThird();

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
        final String[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                mDBHelper.UpdateFirstSleep(items[pos]);
                viewFirst();
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
        final String[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //    builder.setTitle("선택");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                mDBHelper.UpdateSecondSleep(items[pos]);
                viewSecond();
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
        final String[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //    builder.setTitle("선택");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                mDBHelper.UpdateThirdSleep(items[pos]);
                viewThird();
            }
        });
        builder.show();
    }

    private void viewFirst() {
        firstFunc.setText(mDBHelper.SelectFirstSleep());
    }

    private void viewSecond() {
        secondFunc.setText(mDBHelper.SelectSecondSleep());
    }

    private void viewThird() {
        thirdFunc.setText(mDBHelper.SelectThirdSleep());
    }


}