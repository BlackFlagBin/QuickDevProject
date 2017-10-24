package com.blackflagbin.quickdevproject.mvp.contract;

import com.blackflagbin.common.base.IBasePresenter;
import com.blackflagbin.common.base.IBaseView;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public interface TakePhotoContract {
    interface ITakePhotoModel {
    }

    interface ITakePhotoPresenter extends IBasePresenter {
    }

    interface ITakePhotoView extends IBaseView<Object> {

    }
}
