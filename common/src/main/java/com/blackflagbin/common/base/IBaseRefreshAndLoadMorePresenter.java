package com.blackflagbin.common.base;

import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/9/20.
 */

public interface IBaseRefreshAndLoadMorePresenter extends IBasePresenter {
    Disposable initData(int pageNo);
}
