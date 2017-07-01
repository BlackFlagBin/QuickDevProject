package com.zhidian.quickdevproject.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.zhidian.quickdevproject.common.utils.SharedPreferencesUtils;
import com.zhidian.quickdevproject.di.component.AppComponent;
import com.zhidian.quickdevproject.di.component.DaggerAppComponent;
import com.zhidian.quickdevproject.di.module.AppModule;
import com.zhidian.quickdevproject.di.module.HttpModule;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public class App extends Application {

    private AppComponent mAppComponent;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();
        SharedPreferencesUtils.init(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
