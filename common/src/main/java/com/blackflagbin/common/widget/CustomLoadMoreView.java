package com.blackflagbin.common.widget;

import com.blackflagbin.common.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

public final class CustomLoadMoreView extends LoadMoreView {

    /**
     * 如果返回true，数据全部加载完毕后会隐藏加载更多
     * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
     */
    @Override
    public boolean isLoadEndGone() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0
     */
    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
