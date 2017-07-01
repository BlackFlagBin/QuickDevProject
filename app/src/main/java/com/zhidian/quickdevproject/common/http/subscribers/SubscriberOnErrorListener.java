package com.zhidian.quickdevproject.common.http.subscribers;

public interface SubscriberOnErrorListener<T> {
    void onError(Throwable e);
}
