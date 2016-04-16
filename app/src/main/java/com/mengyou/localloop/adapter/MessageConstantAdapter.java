package com.mengyou.localloop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/28.
 */
public class MessageConstantAdapter extends BaseAdapter {

    private List<String> imageUrl;
    private List<String> names;
    private Context mContext;
    private LayoutInflater mInflater;

    public MessageConstantAdapter(List<String> imageUrl, List<String> names, Context mContext) {
        this.imageUrl = imageUrl;
        this.names = names;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_message_constant, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(imageUrl.get(position),viewHolder.imageIcon, MyImageLoader.newOption());
        viewHolder.txtUsername.setText(names.get(position));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image_icon)
        ImageView imageIcon;
        @Bind(R.id.txt_username)
        TextView txtUsername;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
