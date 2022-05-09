package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private TextView btn_nlog;
    private View btn_kakao_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_kakao_login = findViewById(R.id.lg_kakao_btn);
        btn_nlog = findViewById(R.id.lg_nonlg_btn);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>(){
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {    // 로그인 성공시 처리
                    updateKakaoLogin();
                }
                if(throwable != null) {     // 로그인 실패시

                }

                return null;
            }
        };

        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {  // 해당 기기에 카톡이 설치되어 있는 경우
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else{    //해당 기기 카톡 설치 X시 카톡 웹페이지로 로그인
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });

        // 비회원 로그인 버튼 클릭시 테스트 페이지로 이동
        btn_nlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        updateKakaoLogin();
        getAppKeyHash();
    }

    // 로그인이 되어있는지 안되어있는지 확인 후 button 처리
    private void updateKakaoLogin() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {

                    Intent intent =  new Intent(LoginActivity.this, TestActivity.class);
                    startActivity(intent);
                } else {

                }

                return null;
            }
        });
    }

    /* 카카오 로그인 시 필요한 해시키를 얻는 메소드 */
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }

}