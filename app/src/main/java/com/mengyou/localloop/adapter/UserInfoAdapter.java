package com.mengyou.localloop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/10.
 */
public class UserInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mTitlts;
    private List<String> mValues;
    private Context mContext;
    private User user;
    private LayoutInflater mInflater;

    public UserInfoAdapter(List<String> mTitlts, List<String> mValues, Context mContext) {
        this.mTitlts = mTitlts;
        this.mValues = mValues;
        this.mContext = mContext;
        this.user = AVUser.getCurrentUser(User.class);
        this.mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            UserInfoImageViewHolder viewHolder = new UserInfoImageViewHolder(mInflater.inflate(R.layout.item_userinfo_image, parent, false));
            return viewHolder;
        } else {
            UserInfoViewHolder viewHolder = new UserInfoViewHolder(mInflater.inflate(R.layout.item_userinfo, parent, false));
            return viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(holder.itemView,position);
            }
        });
        if (position == 3) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 48, 0, 0);
            holder.itemView.setLayoutParams(layoutParams);
        }

        if (position == 0) {
            final UserInfoImageViewHolder viewHolder = (UserInfoImageViewHolder) holder;
            viewHolder.txtTitle.setText(mTitlts.get(position));
            AVFile file=user.getAVFile("icon");
            if (file!=null)
            {
                ImageLoader.getInstance().displayImage(file.getUrl(), viewHolder.imgValue, MyImageLoader.newOption());
            }
        }
        else
        {
            UserInfoViewHolder viewHolder= (UserInfoViewHolder) holder;
            if (position==2||position==5)
            {
                viewHolder.imgTonext.setVisibility(View.INVISIBLE);
            }
            viewHolder.txtTitle.setText(mTitlts.get(position));
            viewHolder.txtValue.setText(mValues.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mTitlts.size();
    }

    class UserInfoImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.img_tonext)
        ImageView imgTonext;
        @Bind(R.id.img_value)
        ImageView imgValue;

        public UserInfoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class UserInfoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.img_tonext)
        ImageView imgTonext;
        @Bind(R.id.txt_value)
        TextView txtValue;

        public UserInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

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
    }

    public void setLongClick(View targetView,int position)
    {
        if (onItemLongClick!=null)
        {
            onItemLongClick.onItemLongClick(targetView,position);
        }
    }
}
