package com.zhidian.quickdevproject.common.http.subscribers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.zhidian.quickdevproject.common.http.ErrorHandler;
import com.zhidian.quickdevproject.common.http.progress.ProgressCancelListener;
import com.zhidian.quickdevproject.common.http.progress.ProgressDialogHandler;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;

public class ProgressSubscriber<T> extends ResourceSubscriber<T> implements ProgressCancelListener {

    private final IBaseView             mBaseView;
    private       SubscriberCallBack    mCallBack;
    private       ProgressDialogHandler mProgressDialogHandler;

    private Context mContext;

    public ProgressSubscriber(IBaseView baseView, SubscriberCallBack callBack) {
        if (baseView instanceof Activity) {
            mContext = (Context) baseView;
        }
        if (baseView instanceof Fragment) {
            mContext = ((Fragment) baseView).getActivity();
        }
        mBaseView = baseView;
        mCallBack = callBack;
        mProgressDialogHandler = new ProgressDialogHandler(mContext, this, true);
    }

    @Override
    public void onStart() {
        showProgressDialog();
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
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
                    .sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }
    }
}