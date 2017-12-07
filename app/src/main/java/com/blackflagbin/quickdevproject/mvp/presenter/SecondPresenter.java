package com.blackflagbin.quickdevproject.mvp.presenter;

import com.blackflagbin.quickdevproject.mvp.contract.SecondContract;
import com.blackflagbin.common.base.BasePresenter;
import com.blackflagbin.quickdevproject.mvp.model.SecondModel;

import io.reactivex.disposables.Disposable;

public class SecondPresenter extends BasePresenter<SecondContract.ISecondModel, SecondContract.ISecondView> implements SecondContract.ISecondPresenter {

    public SecondPresenter(SecondContract.ISecondView iSecondView) {
        super(iSecondView);
    }

    @Override
    public SecondContract.ISecondModel getModel() {
        return new SecondModel();
    }

    @Override
    public Disposable initData() {
        return null;
    }
}