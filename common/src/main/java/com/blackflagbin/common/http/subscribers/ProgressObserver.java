package com.blackflagbin.common.http.subscribers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.blackflagbin.common.base.IBaseRefreshAndLoadMoreView;
import com.blackflagbin.common.base.IBaseView;
import com.blackflagbin.common.entity.http.CookieResult;
import com.blackflagbin.common.http.ErrorHandler;
import com.blackflagbin.common.http.progress.ProgressCancelListener;
import com.blackflagbin.common.http.progress.ProgressDialogHandler;
import com.blackflagbin.common.util.CookieDbUtil;

import io.reactivex.observers.ResourceObserver;

public class ProgressObserver<T> extends ResourceObserver<T> implements ProgressCancelListener {

    private final IBaseView             mBaseView;
    private final boolean               mIsCache;
    private final String                mUrl;
    private final boolean               mIsLoadMore;
    private       ObserverCallBack      mCallBack;
    private       ProgressDialogHandler mProgressDialogHandler;

    private Context mContext;

    public ProgressObserver(boolean isCache, boolean isLoadMore, String url, IBaseView baseView, ObserverCallBack callBack) {
        if (baseView instanceof Activity) {
            mContext = (Context) baseView;
        }
        if (baseView instanceof Fragment) {
            mContext = ((Fragment) baseView).getActivity();
        }
        mIsCache = isCache;
        mIsLoadMore = isLoadMore;
        mUrl = url;
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
        if (mIsCache) {
            /*获取缓存数据*/
            CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(mUrl);
            if (cookieResult == null) {
                if (mIsLoadMore) {
                    if (mBaseView instanceof IBaseRefreshAndLoadMoreView) {
                        mBaseView.showTip("无网络");
                        ((IBaseRefreshAndLoadMoreView) mBaseView).afterLoadMoreError(e);
                    }
                } else {
                    ErrorHandler.handleError(e, mBaseView);
                    if (mCallBack != null) {
                        mCallBack.onError(e);
                    }
                }
            } else {
                String result = cookieResult.getResult();
                if (mCallBack != null) {
                    mCallBack.onCacheNext(result);
                }
            }
        } else {
            ErrorHandler.handleError(e, mBaseView);
            if (mCallBack != null) {
                mCallBack.onError(e);
            }
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
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

}