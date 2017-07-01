package com.zhidian.quickdevproject.di.module;

import com.zhidian.quickdevproject.common.http.ApiService;
import com.zhidian.quickdevproject.di.scope.ActivityScope;
import com.zhidian.quickdevproject.mvp.contract.MainContract;
import com.zhidian.quickdevproject.mvp.model.MainModel;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by blackflagbin on 2017/6/28.
 */
@Module
public class MainModule {

    private MainContract.View mView;

    public MainModule(MainContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public MainContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public MainContract.Model provideModel(ApiService apiService) {
        return new MainModel(apiService);
    }
}
