package com.zhidian.quickdevproject.ui.activity;

import com.zhidian.quickdevproject.R;
import com.zhidian.quickdevproject.di.component.AppComponent;
import com.zhidian.quickdevproject.di.component.DaggerMainComponent;
import com.zhidian.quickdevproject.di.module.MainModule;
import com.zhidian.quickdevproject.mvp.contract.MainContract;
import com.zhidian.quickdevproject.mvp.presenter.MainPresenter;
import com.zhidian.quickdevproject.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @Override
    public void showTip(String tipMsg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showSuccessView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(String errorMsg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void setupView() {

    }
}
