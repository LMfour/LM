package com.lm.stopsleeping;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "a58703f89eb4b84d90b13f538d1a1a6e");
    }
}
