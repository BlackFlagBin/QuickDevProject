package com.zhidian.quickdevproject.di.module;

import android.app.Application;

import com.zhidian.quickdevproject.application.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by blackflagbin on 2017/6/28.
 */
@Module
public class AppModule<T extends Application> {

    private T mApplication;

    public AppModule(T application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    public T provideApplication() {
        return mApplication;
    }
}
