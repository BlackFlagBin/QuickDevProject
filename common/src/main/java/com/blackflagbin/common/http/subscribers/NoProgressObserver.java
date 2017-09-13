package com.blackflagbin.common.http.subscribers;


import com.blackflagbin.common.base.IBaseRefreshAndLoadMoreView;
import com.blackflagbin.common.base.IBaseView;
import com.blackflagbin.common.entity.http.CookieResult;
import com.blackflagbin.common.http.ErrorHandler;
import com.blackflagbin.common.util.CookieDbUtil;

import io.reactivex.observers.ResourceObserver;

public class NoProgressObserver<T> extends ResourceObserver<T> {

    private final boolean                     mIsCache;
    private final IBaseView mBaseView;
    private final String                      mUrl;
    private final boolean                     mIsLoadMore;
    private       ObserverCallBack            mCallBack;

    public NoProgressObserver(boolean isCache, boolean isLoadMore, String url, IBaseView baseView, ObserverCallBack callBack) {
        mIsCache = isCache;
        mIsLoadMore = isLoadMore;
        mUrl = url;
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
    }

    @Override
    public void onComplete() {

    }


}