package com.mengyou.localloop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengyou.localloop.MessageActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.Message;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/8.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public MessageAdapter(List<Message> mDatas, Context mContext, LayoutInflater mInflater) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MessageViewHolder viewHolder = new MessageViewHolder(mInflater.inflate(R.layout.item_message, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message=mDatas.get(position);
        holder.imageIcon.setImageResource(message.getIconUrl());
        holder.txtUsername.setText(message.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MessageActivity.class);
                intent.putExtra("from","messages");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_icon)
        ImageView imageIcon;
        @Bind(R.id.txt_username)
        TextView txtUsername;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
