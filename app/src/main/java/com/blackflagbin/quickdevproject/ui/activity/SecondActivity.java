package com.blackflagbin.quickdevproject.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.blackflagbin.common.base.BaseActivity;
import com.blackflagbin.quickdevproject.R;
import com.blackflagbin.quickdevproject.common.http.ApiService;
import com.blackflagbin.quickdevproject.mvp.contract.SecondContract;
import com.blackflagbin.quickdevproject.mvp.contract.SecondContract.ISecondPresenter;
import com.blackflagbin.quickdevproject.mvp.presenter.SecondPresenter;
import com.kennyc.view.MultiStateView;
import com.trello.rxlifecycle2.RxLifecycle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SecondActivity extends BaseActivity<ApiService, ISecondPresenter, Object> implements SecondContract.ISecondView {

    @Override
    public SwipeRefreshLayout getSwipeRefreshView() {
        return (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    @Override
    public MultiStateView getMultiStateView() {
        return (MultiStateView) findViewById(R.id.multi_state_view);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_second;
    }

    @Override
    public ISecondPresenter getPresenter() {
        return new SecondPresenter(this);
    }

    @Override
    public void init() {
        Disposable disposable = mPresenter.initData();
        addDisposable(disposable);

        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).compose(this.<Long>bindToLifecycle()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Toast.makeText(SecondActivity.this, "close", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showContentView(Object data) {

    }

}
