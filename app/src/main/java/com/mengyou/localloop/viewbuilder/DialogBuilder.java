package com.mengyou.localloop.viewbuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mengyou.localloop.R;

/**
 * Created by Administrator on 2016/3/10.
 */
public class DialogBuilder {

    public static AlertDialog.Builder builderItemDialog(Context context, int stringId,String title,DialogInterface.OnClickListener onClickListener)
    {
         return new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(stringId,onClickListener);
    }

    public static AlertDialog.Builder builderInputDialog(Context context,View view,String title,DialogInterface.OnClickListener onClickListener)
    {
        return  new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("确认", onClickListener);
    }

    public static AlertDialog.Builder builderNormalDialog(Context context,String title,String message,DialogInterface.OnClickListener onClickListener)
    {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确认", onClickListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    /**
     * 创建加载对话框
     *
     * @param context
     * @return
     */
    public static AlertDialog loadDialog(Context context,String content) {
        View loadView = LayoutInflater.from(context).inflate(R.layout.dialog_load, null);
        TextView txt_load = (TextView) loadView.findViewById(R.id.txt_load);
        txt_load.setText(content);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(loadView);
        return builder.create();
    }
}
