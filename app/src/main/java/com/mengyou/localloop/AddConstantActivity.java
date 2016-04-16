package com.mengyou.localloop;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.mengyou.library.Constant;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.NormalTools;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.library.view.SideBar;
import com.mengyou.localloop.adapter.AddConstantAdapter;
import com.mengyou.localloop.leancloudim.AVImClientManager;
import com.mengyou.localloop.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 添加联系人到消息队列中
 */
public class AddConstantActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.list_contacts)
    StickyListHeadersListView listContacts;
    @Bind(R.id.refresh_contacts)
    SwipeRefreshLayout refreshContacts;
    @Bind(R.id.tv_key)
    TextView tvKey;
    @Bind(R.id.kp_keys)
    SideBar kpKeys;

    private MenuItem menuItem;

    private AddConstantAdapter adapter;
    private Map<String, Integer> maps;
    private Map<String,String> isAddUsers;
    private AVIMConversation squareConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constant);
        mContext=this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        isAddUsers = new HashMap<>();
        User user = AVUser.getCurrentUser(User.class);
        AVRelation<User> relation = user.getRelation("friends");
        relation.getQuery().orderByAscending("pinyinindex").
                findInBackground(new FindCallback<User>() {
                    @Override
                    public void done(final List<User> list, AVException e) {
                        listContacts.setAdapter(adapter = new AddConstantAdapter(list, mContext, new AddConstantAdapter.IsAddCallack() {
                            @Override
                            public void callback(CompoundButton view, boolean isadd, int position) {
                                if (isadd)
                                {
                                    isAddUsers.put(list.get(position).getObjectId(),list.get(position).getNickName());
                                }
                                else
                                {
                                    isAddUsers.remove(list.get(position).getObjectId());
                                }
                                if (isAddUsers.size()==0)
                                {
                                    menuItem.setEnabled(false);
                                }
                                else
                                {
                                    menuItem.setEnabled(true);
                                }
                            }
                        }));
                        int size = list.size();
                        maps = new HashMap<String, Integer>();
                        List<String> datas = new ArrayList<String>();
                        for (int i = 0; i < size; i++) {
                            boolean isAdd = true;
                            String value = list.get(i).getPinyinindex().substring(0, 1);
                            Set<String> set = maps.keySet();
                            if (set.size() == 0) {
                                isAdd = true;
                            } else {
                                for (String key : set) {
                                    if (value.equals(key)) {
                                        isAdd = false;
                                        break;
                                    }
                                }
                            }
                            if (isAdd) {
                                if (NormalTools.isNumeric(value)) {
                                    datas.add("#");
                                    maps.put("#", i);
                                } else {
                                    value = value.toUpperCase();
                                    datas.add(value);
                                    maps.put(value, i);
                                }
                            }
                        }
                        kpKeys.setEntriesList(datas);
                    }
                });
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toOtherActivity(MessageActivity.class, Constant.MEMBER_ID, JSON.toJSONString(isAddUsers), Constant.IntentExtra.FROM, "AddConstantActivity");
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_constant, menu);
        menuItem=menu.getItem(0);
        if (isAddUsers.size()==0)
        {
            menuItem.setEnabled(false);
        }
        else
        {
            menuItem.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_confrim_add_constant)
        {
            toOtherActivity(MessageActivity.class,"addContent", JSON.toJSONString(isAddUsers), Constant.IntentExtra.FROM, "AddConstantActivity");
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("添加好友");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        kpKeys.setOnKeySelectedListener(new SideBar.OnKeySelectedListener() {
            @Override
            public void onSelected(String key) {
                tvKey.setText(key);
                listContacts.smoothScrollToPositionFromTop(maps.get(key), 0);
            }

            @Override
            public void onDown() {
                tvKey.setVisibility(View.VISIBLE);
                kpKeys.setBackgroundColor(Color.parseColor("#9e9e9e"));
            }

            @Override
            public void onUp() {
                tvKey.setVisibility(View.GONE);
                kpKeys.setBackgroundColor(Color.TRANSPARENT);
            }
        });
    }
}
