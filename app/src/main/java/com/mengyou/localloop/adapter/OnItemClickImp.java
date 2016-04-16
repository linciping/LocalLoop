package com.mengyou.localloop.adapter;

import android.view.View;

/**
 * Created by Administrator on 2016/3/8.
 */
public class OnItemClickImp {
    public interface OnItemClick {
        void onItemClick(View targetView, int position);
    }

    public interface OnItemLongClick {
        void onItemLongClick(View targetView,int position);
    }

    private OnItemClick onItemClick;
    private OnItemLongClick onItemLongClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnItemLongClick(OnItemLongClick onItemLongClick) {
        this.onItemLongClick = onItemLongClick;
    }

    public void setClick(View targetView,int position)
    {
        if (onItemClick!=null)
        {
            onItemClick.onItemClick(targetView,position);
        }
        else
        {
            throw new NullPointerException("Item单击事件未初始化!");
        }
        if (onItemLongClick!=null)
        {
            onItemLongClick.onItemLongClick(targetView,position);
        }
        else
        {
            throw  new NullPointerException("Item长按事件为初始化!");
        }
    }
}
