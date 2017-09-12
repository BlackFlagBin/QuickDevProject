package com.blackflagbin.common.http.subscribers;

/**
 * Created by blackflagbin on 2017/7/20.
 */

public interface ObserverCallBack<T> {
    void onNext(T t);

    void onCacheNext(String dataString);

    void onError(Throwable e);

}
