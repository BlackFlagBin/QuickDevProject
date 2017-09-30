package com.blackflagbin.quickdevproject.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blackflagbin.common.base.BaseTakePhotoActivity;
import com.blackflagbin.quickdevproject.R;
import com.blackflagbin.quickdevproject.common.http.ApiService;
import com.blackflagbin.quickdevproject.common.util.CustomHelper;
import com.blackflagbin.quickdevproject.mvp.contract.TakePhotoContract;
import com.jph.takephoto.model.TResult;
import com.kennyc.view.MultiStateView;
@Route(path = "/QuickDevProject/TakePhotoActivity")
public class TakePhotoActivity extends BaseTakePhotoActivity<ApiService, TakePhotoContract.ITakePhotoPresenter, Object>  implements TakePhotoContract.ITakePhotoView {
    private CustomHelper customHelper;

    @Override
    protected SwipeRefreshLayout getSwipeRefreshView() {
        return null;
    }

    @Override
    protected MultiStateView getMultiStateView() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected TakePhotoContract.ITakePhotoPresenter getPresenter() {
        return null;
    }

    @Override
    protected void init() {
        View contentView= LayoutInflater.from(this).inflate(R.layout.activity_take_photo,null);
        customHelper=CustomHelper.of(contentView);
    }

    @Override
    protected void showContentView(Object data) {

    }

    public void onClick(View view) {
        customHelper.onClick(view,getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
    }
}
