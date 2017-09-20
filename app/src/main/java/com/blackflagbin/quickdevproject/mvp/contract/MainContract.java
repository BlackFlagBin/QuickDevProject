package com.blackflagbin.quickdevproject.mvp.contract;

import com.blackflagbin.common.base.IBasePresenter;
import com.blackflagbin.common.base.IBaseRefreshAndLoadMorePresenter;
import com.blackflagbin.common.base.IBaseRefreshAndLoadMoreView;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public interface MainContract {
    interface IMainModel {
        Observable<List<Entity>> getDataList(int pageNo);
    }

    interface IMainPresenter extends IBaseRefreshAndLoadMorePresenter{
    }

    interface IMainView extends IBaseRefreshAndLoadMoreView<List<Entity>> {

    }
}
