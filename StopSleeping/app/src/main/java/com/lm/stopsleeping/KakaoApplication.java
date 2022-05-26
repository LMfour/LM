package com.lm.stopsleeping;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "4c6c6e6678afebc2cede2163a7b2a43f");
    }
}
