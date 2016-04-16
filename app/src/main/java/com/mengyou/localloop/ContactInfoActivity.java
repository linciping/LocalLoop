package com.mengyou.localloop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mengyou.library.Constant;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 联系人信息
 */
public class ContactInfoActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.img_icon)
    ImageView imgIcon;
    @Bind(R.id.img_sex_icon)
    ImageView imgSexIcon;
    @Bind(R.id.btn_level)
    Button btnLevel;
    @Bind(R.id.txt_username)
    TextView txtUsername;
    @Bind(R.id.txt_number)
    TextView txtNumber;
    @Bind(R.id.img_toinfo)
    ImageView imgToinfo;
    @Bind(R.id.txt_confirm)
    TextView txtConfirm;
    @Bind(R.id.txt_info)
    TextView txtInfo;
    @Bind(R.id.txt_where)
    TextView txtWhere;
    @Bind(R.id.img_where)
    TextView imgWhere;
    @Bind(R.id.txt_photo)
    TextView txtPhoto;
    @Bind(R.id.btn_toNext)
    RelativeLayout btnToNext;
    @Bind(R.id.img_tonext)
    ImageView imgTonext;
    @Bind(R.id.btn_sendmessage)
    Button btnSendmessage;
    @Bind(R.id.btn_sendvideo)
    Button btnSendvideo;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        ButterKnife.bind(this);
        mContext = this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        TAG = getClass().getSimpleName();
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        user = JSON.parseObject(getIntent().getStringExtra("user"), User.class);
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
        ImageLoader.getInstance().displayImage(user.getIconUrl(), imgIcon, MyImageLoader.newOption());
        if (user.getSexMy() == 1) {
            imgSexIcon.setImageResource(R.drawable.icon_man);
        } else {
            imgSexIcon.setImageResource(R.drawable.icon_woman);
        }
        txtUsername.setText(user.getNickNameMy());
        btnLevel.setText("v" + user.getRankMy());
        txtInfo.setText(user.getCertificationinfoMy());
        imgWhere.setText(user.getAreaMy());

        btnSendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra(Constant.MEMBER_ID,user.getObjectId());
                intent.putExtra(Constant.MEMBER_NAME,user.getNickNameMy());
                startActivity(intent);
            }
        });

        btnToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity(PhotosActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestory");
    }
}
