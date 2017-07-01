package com.zhidian.quickdevproject.di.component;

import com.zhidian.quickdevproject.application.App;
import com.zhidian.quickdevproject.common.http.ApiService;
import com.zhidian.quickdevproject.di.module.AppModule;
import com.zhidian.quickdevproject.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by blackflagbin on 2017/6/28.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    ApiService getApiService();

    App getApplication();
}
