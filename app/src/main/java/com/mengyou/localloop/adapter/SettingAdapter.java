package com.mengyou.localloop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.mengyou.localloop.AboutAppInfoActivity;
import com.mengyou.localloop.LoginActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.User;

import java.lang.ref.PhantomReference;
import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public SettingAdapter(List<String> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SettingViewHolder viewHolder = new SettingViewHolder(mInflater.inflate(R.layout.item_setting, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        if (position == 2) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 48, 0, 0);
            holder.itemView.setLayoutParams(layoutParams);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage("是否退出当前账号\n(退出将会清除当前用户的数据！！)")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    User user = AVUser.getCurrentUser(User.class);
                                    user.logOut();
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    mContext.startActivity(intent);
                                }
                            });
                    builder.create().show();
                }
            });
        }
        else if (position==1)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AboutAppInfoActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        holder.txt_title.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class SettingViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private ImageView img_tonext;

        public SettingViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            img_tonext = (ImageView) itemView.findViewById(R.id.image_tonext);
        }
    }
}
