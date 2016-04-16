package com.mengyou.library.module;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author linciping
 * @version 1.0
 * @since 1.6
 * 基础Fragement组件
 */
public class BaseFragment extends Fragment {

    protected Context mContext;

    protected View initView(LayoutInflater inflater,ViewGroup container) {
        return null;
    }

    protected void initData() {

    }

    protected void close() {

    }

    protected void toOtherActivity(Class<?> otherActivity) {
        Intent intent = new Intent(mContext, otherActivity);
        startActivity(intent);
    }
}
