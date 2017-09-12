package com.blackflagbin.quickdevproject.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blackflagbin.common.BuildConfig;
import com.blackflagbin.common.facade.CommonLibrary;
import com.blackflagbin.common.listener.OnTokenExpiredListener;
import com.blackflagbin.quickdevproject.common.http.ApiService;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public class App extends Application {


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonLibrary.getInstance().init(this, BuildConfig.APP_URL, ApiService.class, new OnTokenExpiredListener() {
            @Override
            public void onTokenExpired() {

            }
        });
        initThirdPart();
    }

    /**
     * 初始化第三方
     */
    private void initThirdPart() {

    }


}
