package com.zhidian.quickdevproject.mvp.presenter;

import android.util.Log;

import com.zhidian.quickdevproject.common.entity.datacapsule.MainDataEntity;
import com.zhidian.quickdevproject.mvp.contract.MainContract;
import com.zhidian.quickdevproject.ui.base.BasePresenter;
import com.zhidian.quickdevproject.ui.base.IBasePresenter;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> implements
        IBasePresenter {
    @Inject
    public MainPresenter(
            MainContract.Model model, MainContract.View view) {
        super(model, view);
    }

    @Override
    public void initData() {
        mModel.getDataFromBaidu().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("MainPresenter", s);
                mView.showSuccessView();
                mView.setupView(new MainDataEntity(s));
            }
        });
    }
}
