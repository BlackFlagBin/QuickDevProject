package com.zhidian.quickdevproject.di.module;

import com.zhidian.quickdevproject.common.http.ApiService;
import com.zhidian.quickdevproject.common.http.ServiceFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by blackflagbin on 2017/6/28.
 */
@Module
public class HttpModule {
    @Singleton
    @Provides
    public ApiService provideApiService() {
        return ServiceFactory.createUserService(ApiService.class);
    }
}
