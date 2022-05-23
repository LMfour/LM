package com.lm.stopsleeping;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "9f214f51bde3bf2555a4d0d0b1a9666b");
    }
}
