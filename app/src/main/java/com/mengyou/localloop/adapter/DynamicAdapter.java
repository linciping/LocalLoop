package com.mengyou.localloop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.mengyou.localloop.PhotosActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.AVDynamic;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.entity.Photo;

/**
 * Created by Administrator on 2016/3/8.
 */
public class DynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AVDynamic> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private GridImageAdapter adapter;

    public DynamicAdapter(List<AVDynamic> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            DynamicHeaderViewHolder headerViewHolder = new DynamicHeaderViewHolder(mInflater.inflate(R.layout.item_dynamic_header, parent, false));
            return headerViewHolder;
        } else {
            DynamicViewHolder viewHolder = new DynamicViewHolder(mInflater.inflate(R.layout.item_dynamic, parent, false));
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            User user = User.getCurrentUser(User.class);
            DynamicHeaderViewHolder headerViewHolder = (DynamicHeaderViewHolder) holder;
            headerViewHolder.imageCover.setImageResource(R.drawable.image01);
            ImageLoader.getInstance().displayImage(user.getAVFile("icon").getThumbnailUrl(false, 48 * 3, 48 * 3),
                    headerViewHolder.imageIcon, MyImageLoader.newOption());
            headerViewHolder.imageCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra("imageUrl", MyImageLoader.formatDrawable(R.drawable.image01));
                    mContext.startActivity(intent);
                }
            });
            headerViewHolder.imageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PhotosActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else {
            final DynamicViewHolder viewHolder = (DynamicViewHolder) holder;
            final AVDynamic dynamic = mDatas.get(position - 1);

            viewHolder.txtDynamic.setText(dynamic.getContentStr());
            viewHolder.txtTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(dynamic.getCreatedAt()));

            dynamic.getAVObject("creater").fetchIfNeededInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    final User user = (User) avObject;
                    user.setIconUrl(user.getAVFile("icon").getUrl());
                    ImageLoader.getInstance().displayImage(user.getIconUrl(), viewHolder.imageIcon, MyImageLoader.newOption());
                    viewHolder.txtUsername.setText(user.getNickName());
                    viewHolder.btnLevel.setText("v" + user.getRank());
                    viewHolder.imageIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ContactInfoActivity.class);
                            intent.putExtra("user", JSON.toJSONString(user));
                            mContext.startActivity(intent);
                        }
                    });
                }
            });

            //region 设置图片内容
            final List<AVObject> files = dynamic.getList("contentPic");
            if (files != null && files.size() > 0) {//根据图片文件的数量，来决定显示列表或是图片控件
                final List<String> images=new ArrayList<>();
                viewHolder.gridImages.setVisibility(View.VISIBLE);
                for (int i = 0; i < files.size(); i++) {
                    AVFile.withObjectIdInBackground(files.get(i).getObjectId(), new GetFileCallback<AVFile>() {
                        @Override
                        public void done(AVFile avFile, AVException e) {
                            images.add(avFile.getUrl());
                            if (images.size() == files.size()) {
                                viewHolder.gridImages.setAdapter(adapter = new GridImageAdapter(images, mContext));
                                setListViewHeightBasedOnChildren(viewHolder.gridImages);
                                adapter.notifyDataSetChanged();
                                viewHolder.gridImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String imageUrl=images.get(position);
                                        Intent intent=new Intent(mContext,ImageActivity.class);
                                        intent.putExtra("imageUrl",imageUrl);
                                        mContext.startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }
            } else {
                viewHolder.gridImages.setVisibility(View.GONE);
            }
            //endregion

            viewHolder.imageReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    //regionGridView高度自适配

    /**
     * GridView高度自适配
     *
     * @param listView 需要适配高度GridView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((GridView.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }
    //endregion

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    class DynamicHeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_cover)
        ImageView imageCover;
        @Bind(R.id.image_icon)
        ImageView imageIcon;

        public DynamicHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DynamicViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_icon)
        ImageView imageIcon;
        @Bind(R.id.txt_username)
        TextView txtUsername;
        @Bind(R.id.btn_level)
        Button btnLevel;
        @Bind(R.id.txt_dynamic)
        TextView txtDynamic;
        @Bind(R.id.txt_time)
        TextView txtTime;
        @Bind(R.id.image_reply)
        ImageView imageReply;
        @Bind(R.id.grid_images)
        GridView gridImages;

        public DynamicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
