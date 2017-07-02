package com.zhidian.quickdevproject.mvp.contract;

import com.zhidian.quickdevproject.common.entity.datacapsule.MainDataEntity;
import com.zhidian.quickdevproject.common.entity.http.NewsEntity;
import com.zhidian.quickdevproject.ui.base.IBasePresenter;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public interface MainContract {
    interface Model {
        Observable<List<NewsEntity>> getGankNews();
    }

    interface View extends IBaseView<MainDataEntity>{
    }

    interface Presenter extends IBasePresenter {
    }
}
