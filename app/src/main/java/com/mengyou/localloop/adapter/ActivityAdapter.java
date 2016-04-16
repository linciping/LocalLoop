package com.mengyou.localloop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetFileCallback;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.ContactInfoActivity;
import com.mengyou.localloop.ImageActivity;
import com.mengyou.localloop.InfoActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.AVActivity;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 活动页面适配器
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<AVActivity> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public ActivityAdapter(List<AVActivity> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityViewHolder viewHolder = new ActivityViewHolder(mInflater.inflate(R.layout.item_activity, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ActivityViewHolder holder, final int position) {
//        holder.txt_Value.setText(mTitles.get(position));
        final AVActivity activity = mDatas.get(position);
        //region基础数据显示
        if (activity.getState() == 0) {
            holder.activityState.setText("进行中");
            holder.activityState.setBackgroundResource(R.drawable.btn_normal_backgroud);
        } else {
            holder.activityState.setText("已结束");
            holder.activityState.setBackgroundResource(R.drawable.btn_noable_background);
        }
        switch (activity.getTheme()) {
            case 0:
                holder.activityStyleIcon.setImageResource(R.drawable.sport);
                holder.activityName.setText("运动");
                break;
            case 1:
                holder.activityStyleIcon.setImageResource(R.drawable.video);
                holder.activityName.setText("看电影");
                break;
            default:
                holder.activityStyleIcon.setImageResource(R.drawable.travel);
                holder.activityName.setText("旅游");
                break;
        }
        holder.activityInfo.setText(new SimpleDateFormat("yyyy-MM-dd").format(activity.getCreatedAt()));
        setVisivility(holder.activityDescription, activity.getContentStr());
        setVisivility(holder.activityTime, activity.getTime());
        setVisivility(holder.activityWhere, activity.getAddr());
        //endregion
        activity.getAVObject("creater").fetchIfNeededInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                final User user = (User) avObject;
                if (user.getAVFile("icon")!=null)
                {
                    user.setIconUrl(user.getAVFile("icon").getUrl());
                }
                holder.activityUsername.setText(user.getNickName());
                holder.activityLevel.setText("v" + user.getRank());
                if (user.getSex() == 1) {
                    holder.activitySexIcon.setImageResource(R.drawable.icon_man);
                } else {
                    holder.activitySexIcon.setImageResource(R.drawable.icon_woman);
                }
                ImageLoader.getInstance().displayImage(user.getIconUrl(), holder.activityIcon, MyImageLoader.newOption());
                holder.activityIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ContactInfoActivity.class);
                        intent.putExtra("user", JSON.toJSONString(user));
                        mContext.startActivity(intent);
                    }
                });
            }
        });
        List<AVObject> files=activity.getList("contentPic");
        if (files != null && files.size() > 0) {
            if (holder.activityPicture.getVisibility() == View.GONE) {
                holder.activityPicture.setVisibility(View.VISIBLE);
            }
            AVFile.withObjectIdInBackground(files.get(0).getObjectId(), new GetFileCallback<AVFile>() {
                @Override
                public void done(final AVFile avFile, AVException e) {
                    ImageLoader.getInstance().displayImage(avFile.getUrl(), holder.activityPicture, MyImageLoader.newOption());
                    holder.activityPicture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mContext, ImageActivity.class);
                            intent.putExtra("imageUrl",avFile.getUrl());
                            mContext.startActivity(intent);
                        }
                    });
                }
            });
        }
        else {
            holder.activityPicture.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, InfoActivity.class);
                intent.putExtra("targetId",activity.getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    public void setVisivility(TextView textView, String value) {
        if (value == null) {
            textView.setVisibility(View.GONE);
        } else {
            if (value.equals("")) {
                textView.setVisibility(View.GONE);
            } else {
                if (textView.getVisibility() == View.GONE) {
                    textView.setVisibility(View.VISIBLE);
                }
                textView.setText(value);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.activity_icon)
        ImageView activityIcon;
        @Bind(R.id.activity_username)
        TextView activityUsername;
        @Bind(R.id.activity_sex_icon)
        ImageView activitySexIcon;
        @Bind(R.id.activity_state)
        Button activityState;
        @Bind(R.id.activity_level)
        Button activityLevel;
        @Bind(R.id.activity_style_icon)
        ImageView activityStyleIcon;
        @Bind(R.id.activity_name)
        TextView activityName;
        @Bind(R.id.activity_description)
        TextView activityDescription;
        @Bind(R.id.activity_time)
        TextView activityTime;
        @Bind(R.id.activity_where)
        TextView activityWhere;
        @Bind(R.id.activity_info)
        TextView activityInfo;
        @Bind(R.id.activity_picture)
        ImageView activityPicture;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
