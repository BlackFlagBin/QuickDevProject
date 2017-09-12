package com.blackflagbin.common.base;

/**
 * Created by blackflagbin on 2017/9/11.
 */

public interface IBaseRefreshAndLoadMoreView<T> extends IBaseView<T> {
    void beforeInitData();

    void afterLoadMore(T data);

    void afterLoadMoreError(Throwable e);
}
