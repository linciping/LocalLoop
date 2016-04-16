package com.mengyou.localloop.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/25.
 */
public abstract class CommanViewHolder<T> extends RecyclerView.ViewHolder {

    public CommanViewHolder(Context mContext,ViewGroup parent,int layoutRes) {
        super(LayoutInflater.from(mContext).inflate(layoutRes,parent,false));
        ButterKnife.bind(this,itemView);
    }

    public Context getContext()
    {
       return itemView.getContext();
    }

    public abstract void bindData(T t,String url);

    public void setData(T t,String url){
        bindData(t,url);
    };
}
