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

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.LoginManager;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private TextView btn_nlog;
    private View btn_kakao_login;

    private DBHelper mDBHelper;

    private View btn_facebook_login;
    private View btn_naver_login;

    private FacebookLoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setInit();

        btn_kakao_login = findViewById(R.id.lg_kakao_btn);
        btn_nlog = findViewById(R.id.lg_nonlg_btn);
        btn_naver_login = findViewById(R.id.lg_naver_btn);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new FacebookLoginCallback();

        btn_facebook_login = (View) findViewById(R.id.btn_facebook_login);
        btn_facebook_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "email"));
                loginManager.registerCallback(mCallbackManager, mLoginCallback);
            }
        });


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>(){
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {    // ????????? ????????? ??????
                    updateKakaoLogin();
                }
                if(throwable != null) {     // ????????? ?????????

                }

                return null;
            }
        };

        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {  // ?????? ????????? ????????? ???????????? ?????? ??????
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else{    //?????? ?????? ?????? ?????? X??? ?????? ??????????????? ?????????
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });

        // ????????? ????????? ?????? ????????? ????????? ???????????? ??????
        btn_nlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
        // ????????? ????????? ?????? ????????? ????????? ???????????? ??????
        btn_naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        updateKakaoLogin();
        getAppKeyHash();
    }
    // ???????????? ????????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent =  new Intent(LoginActivity.this, TestActivity.class);
        startActivity(intent);
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);
        mDBHelper.UpdateDB();
    }

    // ???????????? ??????????????? ?????????????????? ?????? ??? button ??????
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

    /* ????????? ????????? ??? ????????? ???????????? ?????? ????????? */
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