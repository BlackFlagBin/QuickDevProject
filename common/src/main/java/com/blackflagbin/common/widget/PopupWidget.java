package com.blackflagbin.common.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.blackflagbin.common.R;


public class PopupWidget extends PopupWindow {

    private Context mContext;

    private View view;

    public PopupWidget(Context mContext, View view, CallBack callBack) {

        this.view = callBack.init();

        // 设置外部可点击
        this.setOutsideTouchable(true);
 
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.PopupStyle);

    }

    public interface CallBack {
        View init();
    }
}