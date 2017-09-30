package com.blackflagbin.quickdevproject.mvp.presenter;

import com.blackflagbin.common.base.BasePresenter;
import com.blackflagbin.quickdevproject.mvp.contract.TakePhotoContract;
import com.blackflagbin.quickdevproject.mvp.model.TakePhotoModel;

import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public class TakePhotoPresenter extends BasePresenter<TakePhotoContract.ITakePhotoModel, TakePhotoContract.ITakePhotoView> implements
        TakePhotoContract.ITakePhotoPresenter {


    public TakePhotoPresenter(TakePhotoContract.ITakePhotoView iTakePhotoView) {
        super(iTakePhotoView);
    }

    @Override
    protected TakePhotoContract.ITakePhotoModel getModel() {
        return new TakePhotoModel();
    }


    @Override
    public Disposable initData() {
        return null;
    }
}
