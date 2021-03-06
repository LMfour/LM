package com.lm.stopsleeping;

import static java.sql.Types.NULL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.kakao.sdk.navi.Constants;
import com.kakao.sdk.navi.NaviClient;
import com.kakao.sdk.navi.model.CoordType;
import com.kakao.sdk.navi.model.Location;
import com.kakao.sdk.navi.model.NaviOption;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ServiceActivity";

    private Button btn_stop;
    private FloatingActionButton btn_navi;
    private TextView nickName, email, cnt;
    private FloatingActionButton btn_alarm;
    private FloatingActionButton btn_tomboy;


    MediaPlayer mediaPlayer;

    private ArrayList<DateItem> mDateItems;
    private DBHelper mDBHelper;
    private ImageView btn_rcd;

    private CustomAdapter mAdapter;

    int sw_cnt = 0;
    ImageView imageview = null;

    PreviewView previewView;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};
    final int REQUEST_CODE_PERMISSIONS =0;

    //????????? ?????? ??????
    boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        
        setInit();

        btn_stop=findViewById(R.id.svc_stop_btn);
        btn_navi=findViewById(R.id.svc_loc_btn);
        btn_tomboy=findViewById(R.id.svc_mus_btn);

        nickName = findViewById(R.id.svc_name_txt);
        email = findViewById(R.id.svc_email_txt);


        btn_alarm = findViewById(R.id.svc_alm_btn);

        previewView = findViewById(R.id.svc_previewView);

        Switch sw = (Switch)findViewById(R.id.svc_onoff);
        imageview = (ImageView)findViewById(R.id.svc_img);

        sw.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sw_cnt = 1 - sw_cnt;

                if ( sw_cnt == 1 ){
                    imageview.setImageResource(R.drawable.cam_back);
                }
                else{
                    imageview.setImageResource(NULL);
                }

            }
        });


        //????????? ????????? ?????? ????????? ??????
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runAlarm();
            }
        });


        btn_tomboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer ?????? ??????
                mediaPlayer = MediaPlayer.create(ServiceActivity.this, R.raw.tomboy2);
                mediaPlayer.start();
            }
        });


        // ???????????? ?????? ????????? ????????? ???????????? ??????
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
                builder.setTitle("??????");
                builder.setMessage("????????? ?????????????????????????");

                // ???????????? ?????? ?????? '???' ????????? ????????? ???????????? ??????
                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        

                        Intent intent =  new Intent(ServiceActivity.this, TestActivity.class);
                        startActivity(intent);



                    }
                });
                // ???????????? ?????? ?????? '?????????' ????????? ????????? ?????????
                builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }

        });


        btn_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kakaoNavi();
            }
        });

        updateKakaoLogin();
    }

    //????????? ????????? ?????? ???????????? ?????? ???????????? ???????????? ?????? -> ???????????? ?????? ????????????
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    //????????? ?????? ??????
    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                PreviewView viewFinder = findViewById(R.id.svc_previewView);

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                ProcessCameraProvider cameraProvider = future.get();
                cameraProvider.unbindAll();
                //????????? ??????????????? ????????? ????????? ?????? ??????
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_FRONT_CAMERA, preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void kakaoNavi() {
        if(NaviClient.getInstance().isKakaoNaviInstalled(getApplicationContext())){
            // ??????????????? ????????? ??? ?????? - WGS84
            startActivity(NaviClient.getInstance().navigateIntent(
                    new Location("??????????????? ????????????", "127.34636913237533", "36.75147718869277"),
                    new NaviOption(CoordType.WGS84))
            );

        } else{
            // ??????????????? ?????? ???????????? ??????
            Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEB_NAVI_INSTALL))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void runAlarm() {
        String url = "android.resource://" + getPackageName() + "/";
        int id = getRawResIdByName(mDBHelper.SelectAlarm());
        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(),Uri.parse(url+id));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e){
            Log.e("TAG", "Error: " + e);
        }
    }

    private int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);
        btn_rcd = findViewById(R.id.svc_menu_btn);
        cnt = findViewById(R.id.svc_cnt_txt);
        mDateItems = new ArrayList<>();

        cnt.setText(Integer.toString(mDBHelper.SelectCnt()));

        btn_rcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = updateCnt();
                sleepFunction(count);
            }
        });

    }

    private void sleepFunction(int count) {
        String func;
        if(count == 1){
            func = mDBHelper.SelectFirstSleep();
            sleepFunc(func);
        } else if(count == 2){
            func = mDBHelper.SelectSecondSleep();
            sleepFunc(func);
        } else if(count >= 3){
            func = mDBHelper.SelectThirdSleep();
            sleepFunc(func);
        }
    }

    private void sleepFunc(String func) {
        if(func.equals("??????")){
            runSong();
        } else if(func.equals("??????")){
            runAlarm();
        } else if(func.equals("????????? ??????")){
            kakaoNavi();
        }
    }

    private void runSong() {
        mediaPlayer = MediaPlayer.create(ServiceActivity.this, R.raw.tomboy2);
        mediaPlayer.start();
    }


    private int updateCnt() {
        int sleepCnt = mDBHelper.SelectCnt();
        sleepCnt += 1;
        // Insert Database
        String sleepDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sleepTime = new SimpleDateFormat("a hh:mm:ss").format(new Date());
        mDBHelper.insertRecord(sleepDate, sleepTime);
        mDBHelper.UpdateCnt(sleepCnt);
        cnt.setText(Integer.toString(mDBHelper.SelectCnt()));

        return sleepCnt;
    }

    // ???????????? ??????????????? ?????????????????? ?????? ??? button ??????
    private void updateKakaoLogin() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    email.setText(user.getKakaoAccount().getEmail());
                } else {
                    nickName.setText(null);
                    email.setText(null);
                }

                return null;
            }
        });

    }


}