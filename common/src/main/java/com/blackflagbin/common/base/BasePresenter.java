package com.blackflagbin.common.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.SPUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by Ivan on 2017/1/3.
 */

abstract public class BasePresenter<M, V extends IBaseView> {

    protected M mModel;

    protected V mView;

    protected SPUtils mSPUtils = SPUtils.getInstance();

    protected Context mContext;

    protected LifecycleProvider mLifecycleProvider;


    public BasePresenter(V v) {
        this.mModel = getModel();
        this.mView = v;
        initContext();
    }


    private void initContext() {
        if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getActivity();
        } else {
            mContext = (Activity) mView;
        }
        mLifecycleProvider = (LifecycleProvider) mContext;
    }


    protected abstract M getModel();
}
