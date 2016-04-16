package com.mengyou.localloop.viewbuilder;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.mengyou.localloop.R;

/**
 * Created by Administrator on 2016/3/10.
 */
public class PopupWindowBuilder {

    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private View view;
    private PopupWindow mWindow;


    public PopupWindow createChoosePicture(Context mContext,View.OnClickListener onClickListener)
    {
        mWindow=new PopupWindow(mContext);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        view=inflater.inflate(R.layout.pop_choose_picture,null);
        btn_cancel= (Button) view.findViewById(R.id.btn_cancel);
        btn_pick_photo= (Button) view.findViewById(R.id.btn_pick_photo);
        btn_take_photo= (Button) view.findViewById(R.id.btn_take_photo);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindow.dismiss();
            }
        });

        btn_pick_photo.setOnClickListener(onClickListener);
        btn_take_photo.setOnClickListener(onClickListener);
        mWindow.setContentView(view);
        mWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mWindow.setFocusable(true);
        ColorDrawable drawable=new ColorDrawable(0xb0000000);
        mWindow.setBackgroundDrawable(drawable);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        mWindow.dismiss();
                    }
                }
                return true;
            }
        });
        return mWindow;
    }
}
