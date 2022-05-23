package com.lm.stopsleeping;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "84245f84f98ab84c69d896f82f71efd8");
    }
}
