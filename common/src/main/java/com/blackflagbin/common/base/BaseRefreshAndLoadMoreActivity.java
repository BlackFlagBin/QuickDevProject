package com.blackflagbin.common.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blackflagbin.common.R;
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
    private RecyclerView mRecyclerView;


    @Override
    public void onRefresh() {
        Disposable disposable = mPresenter.initData(1);
        addDisposable(disposable);
    }

    @Override
    public void showSuccessView(D data) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter = getAdapter(data);
        mRecyclerView = getRecyclerView();
        View noDataView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(noDataView);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        showContentView(data);
    }

    protected abstract BaseQuickAdapter getAdapter(D data);

    protected abstract RecyclerView getRecyclerView();

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
        mIsLoadComplete=true;
        mAdapter.loadMoreComplete();
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
