package com.mengyou.localloop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.mengyou.library.Constant;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.SPTools;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.db.MessageCacheManager;
import com.mengyou.localloop.fragment.ChatFragment;
import com.mengyou.localloop.leancloudim.AVImClientManager;
import com.mengyou.localloop.model.MessageCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 聊天窗口
 */
public class MessageActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;


    ChatFragment fragmentChat;
    private String objectId;
    private AVIMConversation squareConversation;
    private boolean isJoin=false;
    String memberId;
    String memberName;
    JSONObject isAddUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mContext=this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity(MainActivity.class,"whereFragment","3");
            }
        });

        fragmentChat= (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_chat);

        String from=getIntent().getStringExtra(Constant.IntentExtra.FROM);

        if (from!=null&&from.equals("AddConstantActivity"))
        {
            isJoin=true;
            memberId= (String) SPTools.get(mContext,Constant.MEMBER_ID,"");
            memberName= (String) SPTools.get(mContext,Constant.MEMBER_NAME,"");
            isAddUsers= JSON.parseObject(getIntent().getStringExtra("addContent"));
            String title=memberName;
            List<String> keys=mapKeyToList();
            for (int i=0;i<keys.size();i++)
            {
                title+=","+isAddUsers.get(keys.get(i));
            }
            setTitle(title);
        }
        else
        {
            isJoin=false;
            memberId= getIntent().getStringExtra(Constant.MEMBER_ID);
            memberName=getIntent().getStringExtra(Constant.MEMBER_NAME);
            SPTools.put(mContext,Constant.MEMBER_ID,memberId);
            SPTools.put(mContext,Constant.MEMBER_NAME,memberName);
            setTitle(memberName);
        }
        if (isJoin)
        {
            queryInSquare((String) SPTools.get(mContext,memberId,""));
        }
        else
        {
            getConversation(memberId);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (null != extras && extras.containsKey(Constant.MEMBER_ID)&&extras.containsKey(Constant.MEMBER_NAME)) {
            String memberId = extras.getString(Constant.MEMBER_ID);
            String memberName=extras.getString(Constant.MEMBER_NAME);
            setTitle(memberName);
            getConversation(memberId);
        }
    }

    /**
     * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {
        final AVIMClient client = AVImClientManager.getInstance().getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(memberId), true);
        conversationQuery.whereEqualTo("customConversationType", 1);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                    if (null != list && list.size() > 0) {
                        squareConversation=list.get(0);
                        SPTools.put(mContext,memberId,squareConversation.getConversationId());
                        fragmentChat.setImConversation(list.get(0));
                        objectId = list.get(0).getConversationId();
                    } else {
                        HashMap<String, Object> attributes = new HashMap<String, Object>();
                        attributes.put("customConversationType", 1);
                        client.createConversation(Arrays.asList(memberId), null, attributes, false, new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                squareConversation=avimConversation;
                                objectId = avimConversation.getConversationId();
                                fragmentChat.setImConversation(avimConversation);
                                SPTools.put(mContext,memberId,squareConversation.getConversationId());
                            }
                        });
                    }
                }
            }
        });
    }


    /**
     * 加入 conversation
     */
    private void joinSquare(String conversationId) {
        final AVIMConversation conversation=AVImClientManager.getInstance().getClient().getConversation(
                conversationId);
        squareConversation=conversation;
        conversation.join(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e==null)
                {
                    conversation.addMembers(mapKeyToList(), new AVIMConversationCallback() {
                        @Override
                        public void done(AVIMException e) {
                            if (e==null)
                            {
                                fragmentChat.setImConversation(conversation);
                            }
                            else
                            {
                                Log.e("addError",e.getMessage());
                            }
                        }
                    });
                }
                else
                {
                    Log.e("addError",e.getMessage());
                }
            }
        });
    }

    private List<String> mapKeyToList() {
        List<String> members=new ArrayList<>();
        Set<String> set=isAddUsers.keySet();
        for (String key:set)
        {
            members.add(key);
        }
        return members;
    }

    /**
     * 先查询自己是否已经在该 conversation，如果存在则直接给 chatFragment 赋值，否则先加入，再赋值
     */
    private void queryInSquare(final String conversationId) {
        final AVIMClient client = AVImClientManager.getInstance().getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.whereEqualTo("objectId", conversationId);
        conversationQuery.containsMembers(mapKeyToList());
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    if (null != list && list.size() > 0) {
                        fragmentChat.setImConversation(list.get(0));
                    } else {
                        joinSquare(conversationId);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_person) {
            toOtherActivity(MessageInfoActivity.class,"objectId",objectId);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        MessageCache messageCache=new MessageCache();
        messageCache.setConversationId(squareConversation.getConversationId());
        messageCache.setCouversationName(squareConversation.getName());
        messageCache.setCreateId(squareConversation.getCreator());
        messageCache.setMemberId(squareConversation.getMembers());
        MessageCacheManager messageCacheManager=new MessageCacheManager(mContext);
        messageCacheManager.insert(messageCache);
        super.onDestroy();
    }
}
