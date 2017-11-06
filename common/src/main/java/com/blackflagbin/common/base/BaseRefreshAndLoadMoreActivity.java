package com.blackflagbin.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blackflagbin.common.R;
import com.blackflagbin.common.widget.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/9/20.
 */

public abstract class BaseRefreshAndLoadMoreActivity<A, P extends IBaseRefreshAndLoadMorePresenter, D> extends BaseActivity<A, P, D> implements
        BaseQuickAdapter.RequestLoadMoreListener, IBaseRefreshAndLoadMoreView<D> {
    public static int PAGE_SIZE = 10;
    protected BaseQuickAdapter mAdapter;
    protected boolean mIsLoadComplete = false;
    protected int     mCurPage        = 1;
    protected RecyclerView               mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = getRecyclerView();
        mLayoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = getAdapter();
        View noDataView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(noDataView);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void showSuccessView(D data) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setNewData((List) data);
        mRecyclerView.setAdapter(mAdapter);
        showContentView(data);
    }

    protected abstract BaseQuickAdapter getAdapter();

    protected abstract RecyclerView getRecyclerView();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void beforeInitData() {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);
        }
        mIsLoadComplete = false;
        mCurPage = 1;
    }

    @Override
    public void afterLoadMore(D list) {
        if (((List) list).size() < PAGE_SIZE) {
            mIsLoadComplete = true;
        } else {
            mIsLoadComplete = false;
        }
        if (((List) list).size() != 0) {
            mAdapter.addData(((List) list));
        }
        mCurPage++;
        mAdapter.loadMoreComplete();
        mSwipeRefresh.setEnabled(true);
    }

    @Override
    public void afterLoadMoreError(Throwable e) {
        mAdapter.loadMoreFail();
        mSwipeRefresh.setEnabled(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefresh.setEnabled(false);
        if (mAdapter.getData().size() < PAGE_SIZE) {
            mAdapter.loadMoreEnd(false);
            mSwipeRefresh.setEnabled(true);
        } else {
            if (mIsLoadComplete) {
                mAdapter.loadMoreEnd();
                mSwipeRefresh.setEnabled(true);
            } else {
                Disposable disposable = mPresenter.initData(mCurPage + 1);
                addDisposable(disposable);
            }
        }
    }
}
