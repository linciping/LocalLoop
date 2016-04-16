package com.mengyou.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.mengyou.library.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class SideBar extends View {


    private static final String TAG="SideBar";

    private String[] mKeys = { "#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };

    private TextPaint mTextPaint;
    private Rect mTextBounds[];

    private float mPadding;

    private String mSeleted;
    private OnKeySelectedListener mListener;

    public void setOnKeySelectedListener(OnKeySelectedListener listener)
    {
        this.mListener=listener;
    }

    public SideBar(Context context) {
        this(context,null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG,"构造方法");
        TypedArray array=context.obtainStyledAttributes(attrs,
                R.styleable.KeyPad,defStyleAttr,0);
        mPadding=array.getDimension(R.styleable.KeyPad_android_padding,20.f);
        int color=array.getColor(R.styleable.KeyPad_android_color, Color.BLUE);
        float defaultTextSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15
            ,getResources().getDisplayMetrics());
        float textSize=array.getDimension(R.styleable.KeyPad_android_textSize,defaultTextSize);
        mTextPaint=new TextPaint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(color);
        if (array.hasValue(R.styleable.KeyPad_android_entries))
        {
            mKeys=context.getResources().getStringArray(array.getResourceId(R.styleable.KeyPad_android_entries,0));
        }
    }

    public void setEntries(String[] entries)
    {
        if (entries==null||entries.length==0) return;
        mKeys=entries;
        requestLayout();
        invalidate();
    }

    public void setEntriesList(List<String> entries)
    {
        String[] datas=entries.toArray(new String[entries.size()]);
        setEntries(datas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG,"onMeasure");
        int width=0;
        int height=0;
        int length=mKeys.length;
        mTextBounds=new Rect[length];

        for (int i=0;i<length;i++)
        {
            mTextBounds[i]=new Rect();
            mTextPaint.getTextBounds(mKeys[i],0,mKeys[i].length(),mTextBounds[i]);
            width=Math.max(width,mTextBounds[i].width());
            height+=mTextBounds[i].height()+mPadding;
        }

//        height-=mPadding;
        width+=getPaddingRight()+getPaddingLeft();
        height+=getPaddingTop()+getPaddingBottom();

        setMeasuredDimension(width+10,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG,"onDraw");
        int length=mKeys.length;
        int center=getMeasuredWidth()/2;
        int height=0;
        for (int i=0;i<length;i++)
        {
            height+=mTextBounds[i].height();
            height+=mPadding;
            canvas.drawText(mKeys[i],center-mTextBounds[i].width()/2
//                +getPaddingLeft()
                    ,height,mTextPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_UP)
        {
            mSeleted=null;
            if (mListener!=null) mListener.onUp();
            return  true;
        }

        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if (mListener!=null) mListener.onDown();
        }

        int y= (int) event.getY();
        int length=mKeys.length;
        int height=getPaddingTop();
        for (int i=0;i<length;i++)
        {
            if (y>=height&&y<=height+mTextBounds[i].height()+mPadding){
                if (mKeys[i].equals(mSeleted)) {
                    break;
                }
                mSeleted=mKeys[i];
                if (mListener!=null){
                    mListener.onSelected(mSeleted);
                }
                break;
            }
            height+=mTextBounds[i].height()+mPadding;
        }
        return true;
    }

    public interface OnKeySelectedListener{
        void onSelected(String key);
        void onDown();
        void onUp();
    }
}
