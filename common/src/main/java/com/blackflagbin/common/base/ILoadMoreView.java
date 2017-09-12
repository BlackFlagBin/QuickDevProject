package com.blackflagbin.common.base;

import java.util.List;

/**
 * Created by blackflagbin on 2017/8/25.
 */

public interface ILoadMoreView<T> {
    void beforeInitData();

    void afterLoadMore(T list);

    void afterLoadMoreError(Throwable e);
}
