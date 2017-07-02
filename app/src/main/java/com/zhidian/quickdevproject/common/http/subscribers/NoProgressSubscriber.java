package com.zhidian.quickdevproject.common.http.subscribers;

import com.zhidian.quickdevproject.common.http.ErrorHandler;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;

public class NoProgressSubscriber<T> extends ResourceSubscriber<T>  {

    private final IBaseView          mBaseView;
    private       SubscriberCallBack mCallBack;

    public NoProgressSubscriber(IBaseView baseView, SubscriberCallBack callBack) {
        mBaseView = baseView;
        mCallBack = callBack;

    }

    @Override
    public void onNext(T t) {
        if (mCallBack != null) {
            mCallBack.onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        ErrorHandler.handleError(e, mBaseView);
        if (mCallBack != null) {
            mCallBack.onError(e);
        }
    }

    @Override
    public void onComplete() {

    }
}