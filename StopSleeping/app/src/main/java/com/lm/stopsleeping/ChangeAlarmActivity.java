package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Toast;

public class ChangeAlarmActivity extends AppCompatActivity {

    ImageButton btn_back;
    MediaPlayer mediaPlayer;
    // 시작버튼
    ImageButton startButton1;
    ImageButton startButton2;
    ImageButton startButton3;
    ImageButton startButton4;
    //종료버튼
    ImageButton stopButton1;
    ImageButton stopButton2;
    ImageButton stopButton3;
    ImageButton stopButton4;
    TextView selAlarm;

    private DBHelper mDBHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_alarm);

        startButton1 = findViewById(R.id.sing_start_btn1);
        stopButton1 = findViewById(R.id.sing_stop_btn1);

        startButton2 = findViewById(R.id.sing_start_btn2);
        stopButton2 = findViewById(R.id.sing_stop_btn2);

        startButton3 = findViewById(R.id.sing_start_btn3);
        stopButton3 = findViewById(R.id.sing_stop_btn3);

        startButton4 = findViewById(R.id.sing_start_btn4);
        stopButton4 = findViewById(R.id.sing_stop_btn4);

        selAlarm = findViewById(R.id.selected_alarm);

        Button button = (Button) findViewById(R.id.alarm_want_btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

        viewAlarm();

        btn_back = findViewById(R.id.men_back_btn);
        // 뒤로가기 버튼 클릭시 메뉴 페이지로 이동
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeAlarmActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


        startButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer 객체 할당
                mediaPlayer = MediaPlayer.create(ChangeAlarmActivity.this, R.raw.siren);
                mediaPlayer.start();
            }
        });

        startButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer 객체 할당
                mediaPlayer = MediaPlayer.create(ChangeAlarmActivity.this, R.raw.dog_sound);
                mediaPlayer.start();
            }
        });

        startButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer 객체 할당
                mediaPlayer = MediaPlayer.create(ChangeAlarmActivity.this, R.raw.break_sound);
                mediaPlayer.start();
            }
        });

        startButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer 객체 할당
                mediaPlayer = MediaPlayer.create(ChangeAlarmActivity.this, R.raw.thunder);
                mediaPlayer.start();
            }
        });



        stopButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정지버튼
                mediaPlayer.stop();
                // 초기화
                mediaPlayer.reset();
            }
        });

        stopButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정지버튼
                mediaPlayer.stop();
                // 초기화
                mediaPlayer.reset();
            }
        });

        stopButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정지버튼
                mediaPlayer.stop();
                // 초기화
                mediaPlayer.reset();
            }
        });

        stopButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정지버튼
                mediaPlayer.stop();
                // 초기화
                mediaPlayer.reset();
            }
        });
    }

    private void viewAlarm() {
        mDBHelper = new DBHelper(this);
        String alarm = mDBHelper.SelectAlarm();
        Log.e("TAG", "SELC" + alarm);

        if(alarm.equals("break_sound")){
            selAlarm.setText("쨍그랑소리");
        } else if(alarm.equals("dog_sound")){
            selAlarm.setText("강아지소리");
        } else if(alarm.equals("siren")){
            selAlarm.setText("사이렌");
        } else if(alarm.equals("thunder")){
            selAlarm.setText("천둥소리");
        }
    }

    void show()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("사이렌");
        ListItems.add("강아지소리");
        ListItems.add("쨍그랑소리");
        ListItems.add("천둥소리");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("노래 선택");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                mDBHelper.UpdateAlarm(items[pos]);
                viewAlarm();
            }
        });
        builder.show();
    }
    // MediaPlayer는 시스템 리소스를 잡아먹는다.
    // MediaPlayer는 필요이상으로 사용하지 않도록 주의해야 한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}