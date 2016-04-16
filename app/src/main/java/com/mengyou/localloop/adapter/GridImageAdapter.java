package com.mengyou.localloop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class GridImageAdapter extends BaseAdapter {

    private List<String> imageUrl;
    private Context mContext;
    private LayoutInflater mInflater;

    public GridImageAdapter(List<String> imageUrl, Context mContext) {
        this.imageUrl = imageUrl;
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return imageUrl.size();
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
        ViewHolder viewHolder;
        if (convertView==null)
        {
            convertView=mInflater.inflate(R.layout.item_gridimage,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(imageUrl.get(position),viewHolder.imageView, MyImageLoader.newOption());
        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;

        public ViewHolder( View itemView) {
            imageView= (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
