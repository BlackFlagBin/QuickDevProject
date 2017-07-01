package com.zhidian.quickdevproject.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.zhidian.quickdevproject.R;
import com.zhidian.quickdevproject.common.entity.datacapsule.MainDataEntity;
import com.zhidian.quickdevproject.di.component.AppComponent;
import com.zhidian.quickdevproject.di.component.DaggerMainComponent;
import com.zhidian.quickdevproject.di.module.MainModule;
import com.zhidian.quickdevproject.mvp.contract.MainContract;
import com.zhidian.quickdevproject.mvp.presenter.MainPresenter;
import com.zhidian.quickdevproject.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.tv_content)
    TextView           mTvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.multi_state_view)
    MultiStateView     mMultiStateView;

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
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(String errorMsg) {

    }

    @Override
    public void setupView(MainDataEntity data) {
        mTvContent.setText(data.baiduData);
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
        mPresenter.initData();
    }

}
