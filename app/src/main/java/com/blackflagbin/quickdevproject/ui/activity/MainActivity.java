package com.blackflagbin.quickdevproject.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blackflagbin.common.base.BaseRefreshAndLoadMoreActivity;
import com.blackflagbin.common.util.CookieDbUtil;
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
public class MainActivity extends BaseRefreshAndLoadMoreActivity<ApiService, IMainPresenter, List<Entity>> implements MainContract.IMainView {

    @BindView(R.id.rv_list)
    RecyclerView    mRvList;
    @BindView(R.id.tv_middle)
    TextView        mTvMiddle;
    @BindView(R.id.bt_delete)
    AppCompatButton mBtDelete;
    @BindView(R.id.multi_state_view)
    MultiStateView  mMultiStateView;

    @Override
    protected SwipeRefreshLayout getSwipeRefreshView() {
        return (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    @Override
    protected MultiStateView getMultiStateView() {
        return (MultiStateView) findViewById(R.id.multi_state_view);
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
        Disposable disposable = mPresenter.initData(1);
        addDisposable(disposable);
    }

    @Override
    protected void showContentView(List<Entity> data) {
        mTvMiddle.setText("福利");
        mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieDbUtil.getInstance().deleteAllCookie();
            }
        });
        mRvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected BaseQuickAdapter getAdapter(List<Entity> data) {
        return new MainListAdapter(data);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRvList;
    }
}
