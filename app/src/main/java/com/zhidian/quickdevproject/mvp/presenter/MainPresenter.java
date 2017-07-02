package com.zhidian.quickdevproject.mvp.presenter;

import com.zhidian.quickdevproject.mvp.contract.MainContract;
import com.zhidian.quickdevproject.ui.base.BasePresenter;
import com.zhidian.quickdevproject.ui.base.IBasePresenter;

import javax.inject.Inject;

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
        mModel.getGankNews()
                .subscribe();
    }
}
