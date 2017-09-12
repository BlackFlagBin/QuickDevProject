package com.blackflagbin.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackflagbin.common.http.HttpProvider;
import com.blankj.utilcode.util.SPUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public abstract class BaseFragment<A , P extends BasePresenter> extends Fragment implements IBaseView {


    protected P                   mPresenter;
    protected SPUtils             mSPUtils;
    protected A                   mApiService;
    private   View                mRootView;
    private   Unbinder            mUnbinder;
    private   CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mPresenter = getPresenter();
        mSPUtils = SPUtils.getInstance();
        mApiService = (A) HttpProvider.getInstance().provideApiService();
        mUnbinder = ButterKnife.bind(this, mRootView);
        mCompositeDisposable = new CompositeDisposable();
        init(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected abstract int getLayoutResId();

    /**
     * 在数据加载之前的一些初始化
     */
    protected abstract void init(View rootView);


    protected abstract P getPresenter();

    protected void addDisposable(Disposable disposable) {
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }
}
