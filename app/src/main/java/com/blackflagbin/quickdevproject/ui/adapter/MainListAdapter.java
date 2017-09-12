package com.blackflagbin.quickdevproject.ui.adapter;

import android.widget.ImageView;

import com.blackflagbin.quickdevproject.R;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public class MainListAdapter extends BaseQuickAdapter<Entity, BaseViewHolder> {


    public MainListAdapter(List<Entity> data) {
        super(R.layout.item_list_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Entity item) {
        ImageView iv = helper.getView(R.id.iv);
        Glide.with(iv.getContext()).load(item.url).into(iv);
    }
}
