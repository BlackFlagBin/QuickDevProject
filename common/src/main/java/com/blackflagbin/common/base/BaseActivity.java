package com.blackflagbin.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blackflagbin.common.R;
import com.blackflagbin.common.constants.Constants;
import com.blackflagbin.common.http.HttpProvider;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public abstract class BaseActivity<A, P extends IBasePresenter, D> extends RxAppCompatActivity implements IBaseView<D>,
        SwipeRefreshLayout.OnRefreshListener {


    protected P                   mPresenter;
    protected SPUtils             mSPUtils;
    protected A                   mApiService;
    protected SwipeRefreshLayout  mSwipeRefresh;
    protected CompositeDisposable mCompositeDisposable;
    protected Unbinder            mUnbinder;
    protected MultiStateView      mMultiStateView;
    protected View                mErrorView;
    protected TextView            mTvErrorMsg;
    protected View                mBtErrorRetry;
    protected View                mEmptyView;
    protected View                mBtEmptyRetry;
    private   Bundle              mBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
    }

    protected  void initView(){
        setContentView(getLayoutResId());
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            onExtraBundleReceived(mBundle);
        }
        mSPUtils=SPUtils.getInstance();
        mPresenter = getPresenter();
        mCompositeDisposable = new CompositeDisposable();
        mApiService = (A) HttpProvider.getInstance().provideApiService();
        mUnbinder = ButterKnife.bind(this);

        mSwipeRefresh = getSwipeRefreshView();
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setOnRefreshListener(this);
        }
        mMultiStateView = getMultiStateView();
        if (mMultiStateView != null) {
            mErrorView = mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
            mBtErrorRetry = mErrorView.findViewById(R.id.bt_retry);
            mEmptyView = mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY);
            mBtEmptyRetry = mEmptyView.findViewById(R.id.bt_retry);
            mBtErrorRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    mPresenter.initData();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mBundle = intent.getExtras();
        if (mBundle != null) {
            onExtraBundleReceived(mBundle);
        }
    }

    protected void onExtraBundleReceived(Bundle bundle) {}


    protected abstract SwipeRefreshLayout getSwipeRefreshView();

    @Override
    public void onRefresh() {
        if (mSwipeRefresh != null) {
            Disposable disposable = mPresenter.initData();
            addDisposable(disposable);
        }
    }

    protected abstract MultiStateView getMultiStateView();

    protected abstract int getLayoutResId();

    protected abstract P getPresenter();


    protected abstract void init();

    protected void addDisposable(Disposable disposable) {
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void startActivity(Class claz, @Nullable Bundle bundle) {
        Intent intent = new Intent(this, claz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showTip(String tipMsg) {
        Toast.makeText(this, tipMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public void dismissLoading() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showSuccessView(D data) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        showContentView(data);
    }

    @Override
    public void showEmptyView() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void showErrorView(String errorMsg) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    protected abstract void showContentView(D data);
}
