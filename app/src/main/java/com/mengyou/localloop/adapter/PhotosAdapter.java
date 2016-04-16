package com.mengyou.localloop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.ImageActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.DateModel;
import com.mengyou.localloop.model.Dymaic;
import com.mengyou.localloop.viewholder.PhotoHeaderHolder;
import com.mengyou.localloop.viewholder.PhotoViewHolder;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/28.
 */
public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dymaic> dynamics;
    private LayoutInflater mInflater;
    private Context mContext;

    public PhotosAdapter(List<Dymaic> dynamics, Context mContext) {
        this.dynamics = dynamics;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0)
        {
           return new PhotoHeaderHolder(mInflater.inflate(R.layout.item_dynamic_header,parent,false));
        }
        else
        {
            return new PhotoViewHolder(mInflater.inflate(R.layout.item_photo,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
       if (position==0)
       {
           return 0;
       }
       else
       {
           return 1;
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PhotoHeaderHolder headerHolder=null;
        PhotoViewHolder photoViewHolder=null;
        if (position==0)
        {
            headerHolder= (PhotoHeaderHolder) holder;
            headerHolder.imageCover.setImageResource(R.drawable.image01);
            headerHolder.imageIcon.setImageResource(R.drawable.icon1);
        }
        else
        {
            photoViewHolder= (PhotoViewHolder) holder;
            photoViewHolder.textView.setText(dynamics.get(position-1).getContent());
            int size=dynamics.get(position-1).getImageUrls().size();
            if (size==1)
            {
                photoViewHolder.gridView.setNumColumns(1);
            }
            else
            {
                photoViewHolder.gridView.setNumColumns(2);
            }
            photoViewHolder.gridView.setAdapter(new ImageAdapter(dynamics.get(position-1).getImageUrls()));
            photoViewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int imageposition, long id) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra("imageUrl", MyImageLoader.formatDrawable(dynamics.get(position-1).getImageUrls().get(imageposition)));
                    mContext.startActivity(intent);
                }
            });
            photoViewHolder.txt_imagecount.setText("共"+dynamics.get(position-1).getImageUrls().size()+"张");
            if (dynamics.get(position-1).isShowTime())
            {
                DateModel dateNow=new DateModel(new Date());
                DateModel model=dynamics.get(position-1).getTime();
                if (dateNow.equals(model))
                {
                    photoViewHolder.txt_month.setText("今");
                    photoViewHolder.txt_day.setTextSize(24);
                    photoViewHolder.txt_day.setText("天");
                }
                else if (model.intervalDay(dateNow)==1)
                {
                    photoViewHolder.txt_month.setText("昨");
                    photoViewHolder.txt_day.setTextSize(24);
                    photoViewHolder.txt_day.setText("天");
                }
                else
                {
                    photoViewHolder.txt_month.setTextSize(22);
                    photoViewHolder.txt_month.setText(model.getDay()+"");
                    photoViewHolder.txt_day.setTextSize(14);
                    photoViewHolder.txt_day.setText(model.getMonth()+"月");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dynamics.size()+1;
    }


    class ImageAdapter extends BaseAdapter {

        private List<Integer> imageUrls;

        ImageAdapter(List<Integer> imageUrls) {
            this.imageUrls = imageUrls;
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView==null)
            {
                convertView=mInflater.inflate(R.layout.item_photo_image,parent,false);
                imageView= (ImageView) convertView.findViewById(R.id.image_value);
                convertView.setTag(imageView);
            }
            else
            {
                imageView= (ImageView) convertView.getTag();
            }
            LinearLayout.LayoutParams params;
            switch (getCount())
            {
                case 1:
                    params=new LinearLayout.LayoutParams(80*3,80*3);
                    imageView.setLayoutParams(params);
                    break;
                case 2:
                    params=new LinearLayout.LayoutParams(80/2*3,80*3);
                    imageView.setLayoutParams(params);
                    break;
                default:
                    params=new LinearLayout.LayoutParams(80/2*3,80/2*3);
                    imageView.setLayoutParams(params);
                    break;
            }
            imageView.setImageResource(imageUrls.get(position));
            return convertView;
        }
    }

}
