package com.zhidian.quickdevproject.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: 只拦截竖直方向的滑动事件，避免多层嵌套带来的滑动冲突的SwipeRefreshLayout Copyright  : Copyright (c) 2016 Company    : zhidian Author     : zhibin Date       :
 * 2016/12/4 16:43
 */
public class NestedSwipeRefreshLayout extends SwipeRefreshLayout {
    private float mDownPosX;
    private float mDownPosY;

    public NestedSwipeRefreshLayout(Context context) {
        super(context);
    }

    public NestedSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
