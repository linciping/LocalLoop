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
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVUser;
import com.mengyou.library.util.LocalImageLoader;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.ContactInfoActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.NearPeople;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.Adapter;

/**
 * Created by Administrator on 2016/3/8.
 */
public class NearPeopleAdapter extends Adapter<NearPeopleAdapter.NearPeopleViewHolder> {

    private List<User> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private AVGeoPoint userPoint;

    public NearPeopleAdapter(List<User> mDatas, Context mContext, AVGeoPoint userPoint) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.userPoint = userPoint;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NearPeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NearPeopleViewHolder viewHolder = new NearPeopleViewHolder(mInflater.inflate(R.layout.item_nearpeople, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NearPeopleViewHolder holder, final int position) {
        final User people = mDatas.get(position);
        AVGeoPoint point = people.getAVGeoPoint("location");
        holder.nearpeopleDescription.setText(people.getSign());
        holder.nearpeopleDistance.setText(Distance(point.getLongitude(), point.getLatitude(), userPoint.getLongitude(), userPoint.getLatitude()) + "米");
        if (people.getSex() == 1) {
            holder.nearpeopleSexIcon.setImageResource(R.drawable.icon_man);
        } else {
            holder.nearpeopleSexIcon.setImageResource(R.drawable.icon_woman);
        }
        if (people.getAVFile("icon")!=null)
        {
            people.setIconUrl(people.getAVFile("icon").getUrl());
            ImageLoader.getInstance().displayImage(people.getAVFile("icon").getUrl(), holder.nearpeopleIcon, MyImageLoader.newOption());
        }
        holder.nearpeopleUsername.setText(people.getNickName());
        holder.nearpeopleLevel.setText("v" + people.getRank());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContactInfoActivity.class);
                intent.putExtra("user", JSON.toJSONString(people));
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setLongClick(holder.itemView, position);
                return false;
            }
        });
    }

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1 第一点经度
     * @param lat1  第一点纬度
     * @param long2 第二点经度
     * @param lat2  第二点纬度
     * @return 返回距离 单位：米
     */
    public static int Distance(double long1, double lat1, double long2,
                               double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return (int) d;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class NearPeopleViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.nearpeople_icon)
        ImageView nearpeopleIcon;
        @Bind(R.id.nearpeople_username)
        TextView nearpeopleUsername;
        @Bind(R.id.nearpeople_sex_icon)
        ImageView nearpeopleSexIcon;
        @Bind(R.id.nearpeople_distance)
        TextView nearpeopleDistance;
        @Bind(R.id.nearpeople_description)
        TextView nearpeopleDescription;
        @Bind(R.id.nearpeople_level)
        Button nearpeopleLevel;

        public NearPeopleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClick {
        void onItemClick(View targetView, int position);
    }

    public interface OnItemLongClick {
        void onItemLongClick(View targetView, int position);
    }

    private OnItemClick onItemClick;
    private OnItemLongClick onItemLongClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnItemLongClick(OnItemLongClick onItemLongClick) {
        this.onItemLongClick = onItemLongClick;
    }

    public void setClick(View targetView, int position) {
        if (onItemClick != null) {
            onItemClick.onItemClick(targetView, position);
        }
    }

    public void setLongClick(View targetView, int position) {
        if (onItemLongClick != null) {
            onItemLongClick.onItemLongClick(targetView, position);
        }
    }
}
