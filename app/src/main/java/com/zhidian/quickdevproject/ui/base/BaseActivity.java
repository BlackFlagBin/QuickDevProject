package com.zhidian.quickdevproject.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhidian.quickdevproject.CommonModule;
import com.zhidian.quickdevproject.application.App;
import com.zhidian.quickdevproject.di.component.AppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    @Inject
    public    P                   mPresenter;
    protected CompositeDisposable mCompositeDisposable;
    private   Unbinder            mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mCompositeDisposable = new CompositeDisposable();
        mUnbinder = ButterKnife.bind(this);
        initComponent(CommonModule.getInstance().getAppComponent());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mCompositeDisposable.dispose();
    }

    protected abstract int getLayoutResId();

    protected abstract void initComponent(AppComponent appComponent);

    /**
     * 在数据加载之前的一些初始化
     */
    protected abstract void init();

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }
}
