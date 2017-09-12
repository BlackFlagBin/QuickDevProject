package com.blackflagbin.quickdevproject.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blackflagbin.common.base.BaseActivity;
import com.blackflagbin.quickdevproject.R;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;
import com.blackflagbin.quickdevproject.common.http.ApiService;
import com.blackflagbin.quickdevproject.mvp.contract.MainContract;
import com.blackflagbin.quickdevproject.mvp.contract.MainContract.IMainPresenter;
import com.blackflagbin.quickdevproject.mvp.presenter.MainPresenter;
import com.blackflagbin.quickdevproject.ui.adapter.MainListAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

@Route(path = "QuickDevProject/MainActivity")
public class MainActivity extends BaseActivity<ApiService, IMainPresenter, List<Entity>> implements MainContract.IMainView,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static int PAGE_SIZE = 10;
    @BindView(R.id.rv_list)
    RecyclerView       mRvList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.multi_state_view)
    MultiStateView     mMultiStateView;
    private View     mErrorView;
    private TextView mTvErrorMsg;
    private View     mBtErrorRetry;
    private View     mEmptyView;
    private View     mBtEmptyRetry;
    private int mCurPage = 1;
    private List<Entity>    mData;
    private MainListAdapter mAdapter;
    private boolean         mIsLoadComplete;

    @Override
    public void beforeInitData() {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);
        }
        mIsLoadComplete = false;
        mCurPage = 1;
    }

    @Override
    public void afterLoadMore(List<Entity> data) {
        if (data != null && data.size() != 0) {
            mData.addAll(data);
        }


        if (data.size() < PAGE_SIZE) {
            mIsLoadComplete = true;
        } else {
            mIsLoadComplete = false;
        }

        mCurPage++;
        mAdapter.loadMoreComplete();
        mSwipeRefresh.setEnabled(true);
    }

    @Override
    public void afterLoadMoreError(Throwable e) {
        mAdapter.loadMoreComplete();
        mSwipeRefresh.setEnabled(true);
    }

    @Override
    public void showLoading() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void dismissLoading() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showSuccessView(List<Entity> data) {
        mData = data;
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        mAdapter = new MainListAdapter(data);
        View noDataView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mRvList.getParent(), false);
        mAdapter.setEmptyView(noDataView);
        mAdapter.setOnLoadMoreListener(this, mRvList);

        mRvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void showEmptyView() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void showErrorView(String errorMsg) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected IMainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void init() {
        mSwipeRefresh.setOnRefreshListener(this);
        mErrorView = mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        mBtErrorRetry = mErrorView.findViewById(R.id.bt_retry);
        mEmptyView = mMultiStateView.getView(MultiStateView.VIEW_STATE_EMPTY);
        mBtEmptyRetry = mEmptyView.findViewById(R.id.bt_retry);
        mBtErrorRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                mPresenter.getDataList(1);
            }
        });
        Disposable disposable = mPresenter.getDataList(1);
        addDisposable(disposable);
    }

    @Override
    public void onRefresh() {
        Disposable disposable = mPresenter.getDataList(1);
        addDisposable(disposable);
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
                Disposable disposable = mPresenter.getDataList(mCurPage + 1);
                addDisposable(disposable);
            }
        }
    }
}
