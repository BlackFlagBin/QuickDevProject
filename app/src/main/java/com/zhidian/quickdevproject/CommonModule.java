package com.zhidian.quickdevproject;

import android.app.Application;

import com.blankj.utilcode.utils.Utils;
import com.zhidian.quickdevproject.di.component.AppComponent;
import com.zhidian.quickdevproject.di.component.DaggerAppComponent;
import com.zhidian.quickdevproject.di.module.AppModule;
import com.zhidian.quickdevproject.di.module.HttpModule;

/**
 * Created by blackflagbin on 2017/7/6.
 */

public class CommonModule {

    private AppComponent mAppComponent;

    private CommonModule() {

    }

    public static CommonModule getInstance() {
        return Holder.instance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public <T extends Application> void init(T application) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .httpModule(new HttpModule())
                .build();
        Utils.init(application);

    }

    private static class Holder {
        static CommonModule instance = new CommonModule();
    }
}
