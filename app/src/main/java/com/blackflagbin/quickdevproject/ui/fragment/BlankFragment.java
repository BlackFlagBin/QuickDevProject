package com.blackflagbin.quickdevproject.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackflagbin.common.base.BaseFragment;
import com.blackflagbin.common.base.IBasePresenter;
import com.blackflagbin.quickdevproject.R;
import com.kennyc.view.MultiStateView;


public class BlankFragment extends BaseFragment {


    @Override
    protected SwipeRefreshLayout getSwipeRefreshView() {
        return null;
    }

    @Override
    protected MultiStateView getMultiStateView() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void showContentView(Object data) {

    }
}
