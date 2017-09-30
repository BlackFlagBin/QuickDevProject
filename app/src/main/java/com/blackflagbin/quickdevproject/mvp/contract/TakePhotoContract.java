package com.blackflagbin.quickdevproject.mvp.contract;

import com.blackflagbin.common.base.IBasePresenter;
import com.blackflagbin.common.base.IBaseRefreshAndLoadMorePresenter;
import com.blackflagbin.common.base.IBaseRefreshAndLoadMoreView;
import com.blackflagbin.common.base.IBaseView;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public interface TakePhotoContract {
    interface ITakePhotoModel {
    }

    interface ITakePhotoPresenter extends IBasePresenter{
    }

    interface ITakePhotoView extends IBaseView<Object> {

    }
}
