package com.mengyou.localloop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Administrator on 2016/3/28.
 */
public class AddConstantAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<User> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private IsAddCallack callack;


    public AddConstantAdapter(List<User> mDatas, Context mContext,IsAddCallack callack) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(mContext);
        this.callack=callack;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView==null)
        {
            convertView=mInflater.inflate(R.layout.item_add_message_constant,parent,false);
            viewHolder=new ViewHolder(convertView);
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
            ImageLoader.getInstance().displayImage(file.getUrl(), viewHolder.imageIcon, MyImageLoader.newOption());
        }
        viewHolder.txtUsername.setText(user.getNickName());
        viewHolder.btnLevel.setText("v"+user.getRank());
        viewHolder.ckbIsAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callack.callback(buttonView,isChecked,position);
            }
        });
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_contacts_header, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + mDatas.get(position).getPinyinindex().charAt(0);
        holder.txtIndex.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mDatas.get(position).getPinyinindex().charAt(0);
    }

    static class ViewHolder {
        @Bind(R.id.image_icon)
        ImageView imageIcon;
        @Bind(R.id.txt_username)
        TextView txtUsername;
        @Bind(R.id.btn_level)
        Button btnLevel;
        @Bind(R.id.ckb_is_add)
        CheckBox ckbIsAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderViewHolder{
        @Bind(R.id.txt_index)
        TextView txtIndex;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    public interface IsAddCallack{
        void callback(CompoundButton view,boolean isadd,int position);
    }
}
