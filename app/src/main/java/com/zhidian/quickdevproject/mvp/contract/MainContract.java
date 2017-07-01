package com.zhidian.quickdevproject.mvp.contract;

import com.zhidian.quickdevproject.common.entity.datacapsule.MainDataEntity;
import com.zhidian.quickdevproject.ui.base.IBasePresenter;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import io.reactivex.Observable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public interface MainContract {
    interface Model {
        Observable<String> getDataFromBaidu();
    }

    interface View extends IBaseView<MainDataEntity>{
    }

    interface Presenter extends IBasePresenter {
    }
}
