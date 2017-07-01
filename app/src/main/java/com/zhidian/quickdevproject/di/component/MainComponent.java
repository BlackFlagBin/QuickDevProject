package com.zhidian.quickdevproject.di.component;

import com.zhidian.quickdevproject.application.App;
import com.zhidian.quickdevproject.common.http.ApiService;
import com.zhidian.quickdevproject.di.module.MainModule;
import com.zhidian.quickdevproject.di.scope.ActivityScope;
import com.zhidian.quickdevproject.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by blackflagbin on 2017/6/28.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);


}
