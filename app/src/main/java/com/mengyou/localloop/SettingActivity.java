package com.mengyou.localloop;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.adapter.SettingAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyler_setting)
    RecyclerView recylerSetting;


    private List<String> mDatas;
    private SettingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext=this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        TAG=getClass().getName();
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mDatas=new ArrayList<>();
        mDatas.add("帮助和反馈");
        mDatas.add("关于本地圈");
        mDatas.add("退出登录");
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recylerSetting.setLayoutManager(new LinearLayoutManager(mContext));
        recylerSetting.setAdapter(adapter = new SettingAdapter(mDatas, mContext));
        recylerSetting.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        mDatas=null;
        adapter=null;
    }
}
