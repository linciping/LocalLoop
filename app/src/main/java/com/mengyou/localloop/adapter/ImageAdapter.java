package com.mengyou.localloop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.PhotosActivity;
import com.mengyou.localloop.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<String> imageUrl;
    private Context mContext;
    private LayoutInflater mInflater;

    public ImageAdapter(List<String> imageUrl, Context mContext) {
        this.imageUrl = imageUrl;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PhotosActivity.class));
            }
        });
        ImageLoader.getInstance().displayImage(imageUrl.get(position),holder.imageView, MyImageLoader.newOption());
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image);
        }
    }


}
