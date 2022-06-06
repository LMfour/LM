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

    //카메라 권한 허가
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


        //권한이 있을시 바로 카메라 시작
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
                // MediaPlayer 객체 할당
                mediaPlayer = MediaPlayer.create(ServiceActivity.this, R.raw.tomboy2);
                mediaPlayer.start();
            }
        });


        // 운전중지 버튼 클릭시 테스트 페이지로 이동
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
                builder.setTitle("안내");
                builder.setMessage("운전을 중지하시겠습니까?");

                // 운전중지 버튼 팝업 '예' 클릭시 테스트 페이지로 이동
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        

                        Intent intent =  new Intent(ServiceActivity.this, TestActivity.class);
                        startActivity(intent);



                    }
                });
                // 운전중지 버튼 팝업 '아니오' 클릭시 팝업창 내려감
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
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

    //초기에 카메라 권한 허가체크 로그 없을시에 실행되는 함수 -> 허가승인 먼저 실행해줌
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

    //카메라 키는 함수
    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                PreviewView viewFinder = findViewById(R.id.svc_previewView);

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                ProcessCameraProvider cameraProvider = future.get();
                cameraProvider.unbindAll();
                //카메라 셀렉터에서 전후면 카메라 설정 가능
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_FRONT_CAMERA, preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void kakaoNavi() {
        if(NaviClient.getInstance().isKakaoNaviInstalled(getApplicationContext())){
            // 카카오내비 앱으로 길 안내 - WGS84
            startActivity(NaviClient.getInstance().navigateIntent(
                    new Location("오창휴게소 하남방향", "127.34636913237533", "36.75147718869277"),
                    new NaviOption(CoordType.WGS84))
            );

        } else{
            // 카카오내비 설치 페이지로 이동
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
        if(func.equals("노래")){
            runSong();
        } else if(func.equals("알람")){
            runAlarm();
        } else if(func.equals("휴게소 안내")){
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

    // 로그인이 되어있는지 안되어있는지 확인 후 button 처리
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