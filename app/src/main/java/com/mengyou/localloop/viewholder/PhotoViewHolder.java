package com.mengyou.localloop.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.mengyou.localloop.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/4/13.
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {

   public GridView gridView;
   public TextView textView;
    public TextView txt_month;
    public TextView txt_day;
    public TextView txt_imagecount;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        gridView= (GridView) itemView.findViewById(R.id.grid_images);
        textView= (TextView) itemView.findViewById(R.id.txt_content);
        txt_month= (TextView) itemView.findViewById(R.id.txt_month);
        txt_day= (TextView) itemView.findViewById(R.id.txt_day);
        txt_imagecount= (TextView) itemView.findViewById(R.id.txt_imagecount);
    }
}
