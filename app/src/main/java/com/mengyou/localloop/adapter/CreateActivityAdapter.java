package com.mengyou.localloop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.ImageActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.event.ActivityImageEvent;
import com.mengyou.localloop.event.CreateAvtivityCallBack;
import com.mengyou.localloop.viewbuilder.DialogBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Administrator on 2016/3/9.
 */
public class CreateActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActiviy;
    private List<String> mDatas;
    private List<String> mTitle;
    private LayoutInflater mInflater;

    private List<String> images=new ArrayList<>();
    private InfoViewHolder infoViewHolder;

    public final static int REQUEST_CODE = 1;

    public CreateActivityAdapter(Activity mActiviy, List<String> mDatas, List<String> mTitle) {
        this.mActiviy = mActiviy;
        this.mDatas = mDatas;
        this.mTitle = mTitle;
        this.mInflater = LayoutInflater.from(mActiviy);
        EventBus.getDefault().register(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new CreateActivityViewHolder(mInflater.inflate(R.layout.item_create_activity, parent, false));
        } else if (viewType == 1)
        {
            return new InfoViewHolder(mInflater.inflate(R.layout.item_info, parent, false));
        }
        else
        {
            return new InfoFooterViewHolder(mInflater.inflate(R.layout.item_create_footer,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 7) {
            return 0;
        }
        else if (position==7)
        {
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClick(holder.itemView,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setLongClick(v,position);
                return false;
            }
        });
        if (position == 3 || position == 5 || position == 7) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 48, 0, 0);
            holder.itemView.setLayoutParams(params);
        }
        if (position < 7) {
            CreateActivityViewHolder viewHolder = (CreateActivityViewHolder) holder;
            viewHolder.txtTitle.setText(mTitle.get(position));
            viewHolder.txtChoose.setText(mDatas.get(position));
        }
        else if (position==7){
            infoViewHolder = (InfoViewHolder) holder;
            images.add("drawable://" + R.drawable.image_add);
            infoViewHolder.imageCover.setAdapter(new CoverAdapter(images));
            infoViewHolder.imageCover.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position==images.size()-1)
                    {
                        PhotoPickerIntent intent = new PhotoPickerIntent(mActiviy);
                        intent.setColumn(4);
                        intent.setPhotoCount(9);
                        mActiviy.startActivityForResult(intent, REQUEST_CODE);
                    }
                    else
                    {
                        String url="file://"+images.get(position);
                        Intent intent=new Intent(mActiviy, ImageActivity.class);
                        intent.putExtra("imageUrl",url);
                        mActiviy.startActivity(intent);
                    }
                }
            });
        }
        else {
            InfoFooterViewHolder viewHolder= (InfoFooterViewHolder) holder;
            viewHolder.checkBox.setChecked(true);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }
    }

    public void onEvent(ActivityImageEvent event)
    {
        Log.e("onevent","have spend");
        images=event.images;
        infoViewHolder.imageCover.setAdapter(new CoverAdapter(images));
        infoViewHolder.imageCover.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == images.size() - 1) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(mActiviy);
                    intent.setColumn(4);
                    intent.setPhotoCount(9);
                    mActiviy.startActivityForResult(intent, REQUEST_CODE);
                } else {
                    String url="file://"+images.get(position);
                    Intent intent=new Intent(mActiviy, ImageActivity.class);
                    intent.putExtra("imageUrl",url);
                    mActiviy.startActivity(intent);
                }
            }
        });
    }

    class CoverAdapter extends BaseAdapter{
        private List<String> imageUrl;
        public CoverAdapter(List<String> imageUrl) {
            this.imageUrl = imageUrl;
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
                convertView=mInflater.inflate(R.layout.item_cover,parent,false);
                viewHolder=new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            if (position==getCount()-1)
            {
                ImageLoader.getInstance().displayImage(imageUrl.get(position),viewHolder.imageView, MyImageLoader.newOption());
            }
            ImageLoader.getInstance().displayImage("file://"+imageUrl.get(position),viewHolder.imageView, MyImageLoader.newOption());
            return convertView;
        }

        class ViewHolder{
            private ImageView imageView;

            public ViewHolder( View itemView) {
                imageView= (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 2;
    }

    class CreateActivityViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.image_tonext)
        ImageView imageTonext;
        @Bind(R.id.txt_choose)
        TextView txtChoose;

        public CreateActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {

        EditText txtDescription;
        GridView imageCover;

        public InfoViewHolder(View itemView) {
            super(itemView);
            txtDescription= (EditText) itemView.findViewById(R.id.txt_description);
            imageCover= (GridView) itemView.findViewById(R.id.grid_images);
        }
    }

    class InfoFooterViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;

        public InfoFooterViewHolder(View itemView) {
            super(itemView);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkbox_create);
        }
    }


    //region Item事件相关
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
    //endregion
}
