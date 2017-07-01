package com.zhidian.quickdevproject.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    @Inject
    public  P                   mPresenter;
    private View                mRootView;
    private Unbinder            mUnbinder;
    private App                 mApp;
    private CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mApp = (App) (getActivity().getApplication());
        initComponent(mApp.getAppComponent());
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mCompositeDisposable = new CompositeDisposable();
        init(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mCompositeDisposable.dispose();
    }

    protected abstract int getLayoutResId();

    /**
     * 在数据加载之前的一些初始化
     */
    protected abstract void init(View rootView);

    protected abstract void initComponent(AppComponent appComponent);

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }
}
