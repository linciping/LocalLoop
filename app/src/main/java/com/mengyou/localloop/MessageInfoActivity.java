package com.mengyou.localloop;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.adapter.MessageConstantAdapter;
import com.mengyou.localloop.leancloudim.AVImClientManager;
import com.mengyou.localloop.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.grid_message_constant)
    GridView gridMessageConstant;
    @Bind(R.id.switch_no_notify)
    Switch switchNoNotify;
    @Bind(R.id.btn_to_report)
    RelativeLayout btnToReport;

    private MessageConstantAdapter adapter;
    private List<String> imageUrls;
    private List<String> names;
    private String objectId;
    private int membersLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        ButterKnife.bind(this);
        mContext=this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        names=new ArrayList<>();
        imageUrls=new ArrayList<>();
        objectId=getIntent().getStringExtra("objectId");
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AVIMConversationQuery query= AVImClientManager.getInstance().getClient().getQuery();
        query.whereEqualTo("objectId", objectId);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {//查询用户
                final List<String> users = list.get(0).getMembers();
                membersLength=users.size();
                final List<User> members=new ArrayList<User>();
                for (int i = 0; i < users.size(); i++) {
                    AVQuery<User> query1=User.getQuery(User.class);
                    query1.whereEqualTo("objectId",users.get(i));
                    query1.findInBackground(new FindCallback<User>() {
                        @Override
                        public void done(List<User> list, AVException e) {
                            members.add(list.get(0));
                            if (members.size()==membersLength)
                            {
                                for (int i=0;i<members.size();i++)
                                {
                                    if (members.get(i).getAVFile("icon")!=null) {
                                        imageUrls.add(members.get(i).getAVFile("icon").getUrl());
                                    }
                                    else
                                    {
                                        imageUrls.add(MyImageLoader.formatDrawable(R.drawable.load_backgroud_64));
                                    }
                                    names.add(members.get(i).getNickName());
                                }
                                gridMessageConstant.setAdapter(adapter = new MessageConstantAdapter(imageUrls, names, mContext));
                                setListViewHeightBasedOnChildren(gridMessageConstant);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

    //regionGridView高度自适配

    /**
     * GridView高度自适配
     *
     * @param listView 需要适配高度GridView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((GridView.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }
    //endregion


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_constant) {
            toOtherActivity(AddConstantActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

}
