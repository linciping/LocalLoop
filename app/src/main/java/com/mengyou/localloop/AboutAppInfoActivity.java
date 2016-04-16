package com.mengyou.localloop;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;

import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.StatusBarBulider;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 关于
 */
public class AboutAppInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app_info);
        mContext=this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("关于本地圈");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
