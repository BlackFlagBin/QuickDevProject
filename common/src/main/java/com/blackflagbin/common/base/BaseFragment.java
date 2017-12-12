package com.blackflagbin.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blackflagbin.common.R;
import com.blackflagbin.common.http.HttpProvider;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public abstract class BaseFragment<A, P extends IBasePresenter, D> extends RxFragment implements IBaseView<D>, SwipeRefreshLayout.OnRefreshListener {


    protected P                   mPresenter;
    protected SPUtils             mSPUtils;
    protected A                   mApiService;
    protected SwipeRefreshLayout  mSwipeRefresh;
    protected MultiStateView      mMultiStateView;
    protected View                mErrorView;
    protected TextView            mTvErrorMsg;
    protected View                mBtErrorRetry;
    protected View                mEmptyView;
    protected View                mBtEmptyRetry;
    protected View                mRootView;
    protected Unbinder            mUnbinder;
    protected CompositeDisposable mCompositeDisposable;


    //标记需要等待回调onMyResume;
    private boolean isWaitingForOnMyResume = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onMyHide();
        } else {
            onMyShow();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (isResumed()) {
                onFragmentVisible();
            } else {
                isWaitingForOnMyResume = true;
            }

        } else {
            if (isResumed()) { onFragmentInvisible(); }
        }
    }

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
        return mRootView;
    }

    // FragmentTransaction 调用show 回调
    protected void onMyShow() {

    }

    // FragmentTransaction 调用hide 回调
    protected void onMyHide() {
    }

    /**
     * viewPager中界面每次可见调用；
     */
    public void onFragmentVisible() {
    }

    /**
     * viewPager中界面每次不可见调用；
     */
    public void onFragmentInvisible() {
    }

    protected void setupView() {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isWaitingForOnMyResume) {
            isWaitingForOnMyResume = false;
            onFragmentVisible();
        }

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

    /**
     * 在数据加载之前的一些初始化
     */
    protected abstract void init();


    protected abstract P getPresenter();

    protected void addDisposable(Disposable disposable) {
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void startActivity(Class claz, @Nullable Bundle bundle) {
        Intent intent = new Intent(getActivity(), claz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showTip(String tipMsg) {
        Toast.makeText(getActivity(), tipMsg, Toast.LENGTH_SHORT).show();
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
