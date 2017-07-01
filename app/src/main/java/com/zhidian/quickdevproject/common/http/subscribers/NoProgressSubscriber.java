package com.zhidian.quickdevproject.common.http.subscribers;

import android.content.Context;

import com.zhidian.quickdevproject.common.http.ErrorHandler;
import com.zhidian.quickdevproject.common.http.progress.ProgressCancelListener;
import com.zhidian.quickdevproject.common.http.progress.ProgressDialogHandler;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;

public class NoProgressSubscriber<T> extends ResourceSubscriber<T> implements
        ProgressCancelListener {

    private final SubscriberOnErrorListener mSubscriberOnErrorListener;
    private final IBaseView                 mIBaseView;
    private       SubscriberOnNextListener  mSubscriberOnNextListener;
    private       ProgressDialogHandler     mProgressDialogHandler;

    private Context mContext;

    public NoProgressSubscriber(
            Context context, IBaseView baseView, SubscriberOnNextListener subscriberOnNextListener,
            SubscriberOnErrorListener subscriberOnErrorListener) {
        mContext = context;
        mIBaseView = baseView;
        mSubscriberOnNextListener = subscriberOnNextListener;
        mSubscriberOnErrorListener = subscriberOnErrorListener;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 对错误进行统一处理 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        ErrorHandler.handleError(e, mContext, mIBaseView);
        if (mSubscriberOnErrorListener != null) {
            mSubscriberOnErrorListener.onError(e);
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}