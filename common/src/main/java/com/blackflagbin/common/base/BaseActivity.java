package com.blackflagbin.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blackflagbin.common.http.HttpProvider;
import com.blankj.utilcode.util.SPUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public abstract class BaseActivity<A , P extends IBasePresenter,D> extends AppCompatActivity implements IBaseView<D> {


    public  P                   mPresenter;
    public  SPUtils             mSPUtils;
    public  A                   mApiService;
    private CompositeDisposable mCompositeDisposable;
    private Unbinder            mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mPresenter = getPresenter();
        mCompositeDisposable = new CompositeDisposable();
        mApiService = (A) HttpProvider.getInstance().provideApiService();
        mSPUtils = SPUtils.getInstance();
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected abstract int getLayoutResId();

    protected abstract P getPresenter();


    /**
     * 在数据加载之前的一些初始化
     */
    protected abstract void init();

    protected void addDisposable(Disposable disposable) {
        if (disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void startActivity(String url, Bundle bundle) {
        if (bundle == null) {
            ARouter.getInstance().build(url).navigation();
        } else {
            ARouter.getInstance().build(url).with(bundle).navigation();
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showTip(String tipMsg) {
        Toast.makeText(this, tipMsg, Toast.LENGTH_SHORT).show();
    }
}
