package com.mengyou.localloop.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mengyou.localloop.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/13.
 */
public class PhotoHeaderHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image_cover)
    public ImageView imageCover;
    @Bind(R.id.image_icon)
    public ImageView imageIcon;

    public PhotoHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
