package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.sdk.navi.Constants;
import com.kakao.sdk.navi.NaviClient;
import com.kakao.sdk.navi.model.CoordType;
import com.kakao.sdk.navi.model.Location;
import com.kakao.sdk.navi.model.NaviOption;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ServiceActivity";

    private Button btn_stop;
    //private FloatingActionButton btn_menu;
    private FloatingActionButton btn_navi;

    private TextView nickName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btn_stop=findViewById(R.id.svc_stop_btn);
        btn_navi=findViewById(R.id.svc_loc_btn);
        //btn_menu=findViewById(R.id.svc_menu_btn);

        nickName = findViewById(R.id.svc_name_txt);
        email = findViewById(R.id.svc_email_txt);

        // 운전중지 버튼 클릭시 테스트 페이지로 이동
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ServiceActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });


        btn_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });



        /*
        // 메뉴 버튼 클릭시 메뉴 페이지로 이동
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ServiceActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
         */

        updateKakaoLogin();
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