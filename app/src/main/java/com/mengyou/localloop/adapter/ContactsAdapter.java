package com.mengyou.localloop.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.Activity;
import com.mengyou.localloop.model.Contact;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ContactsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<User> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public ContactsAdapter(List<User> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater =LayoutInflater.from(mContext);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.item_contacts_header, parent, false);
            holder.txt_index = (TextView) convertView.findViewById(R.id.txt_index);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + mDatas.get(position).getPinyinindex().charAt(0);
        holder.txt_index.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mDatas.get(position).getPinyinindex().charAt(0);
    }

    @Override
    public int getCount() {
        return mDatas.size();
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
        final ViewHolder viewHolder;
        if (convertView==null)
        {
            convertView=mInflater.inflate(R.layout.item_contacts,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.btn_level= (Button) convertView.findViewById(R.id.btn_level);
            viewHolder.image_icon= (ImageView) convertView.findViewById(R.id.image_icon);
            viewHolder.txt_username= (TextView) convertView.findViewById(R.id.txt_username);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        User user=mDatas.get(position);
        AVFile file=user.getAVFile("icon");
        if (file!=null)
        {
            ImageLoader.getInstance().displayImage(file.getUrl(), viewHolder.image_icon, MyImageLoader.newOption());
        }
        viewHolder.txt_username.setText(user.getNickName());
        viewHolder.btn_level.setText("v"+user.getRank());
        return convertView;
    }

    class ViewHolder{
        ImageView image_icon;
        Button btn_level;
        TextView txt_username;
    }
    class HeaderViewHolder{
        TextView txt_index;
    }
}
