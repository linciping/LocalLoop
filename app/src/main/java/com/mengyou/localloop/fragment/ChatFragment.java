package com.mengyou.localloop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.MultipleItemAdapter;
import com.mengyou.localloop.event.ImTypeMessageEvent;
import com.mengyou.localloop.event.ImTypeMessageResendEvent;
import com.mengyou.localloop.event.InputBottomBarTextEvent;
import com.mengyou.localloop.leancloudim.NotificationUtils;
import com.mengyou.localloop.model.AVIMMessageObject;
import com.mengyou.localloop.viewbuilder.AVInputBottomBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/25.
 */
public class ChatFragment extends Fragment {

    @Bind(R.id.recyler_chat)
    RecyclerView recylerChat;
    @Bind(R.id.refresh_chat)
    SwipeRefreshLayout refreshChat;
    @Bind(R.id.fragment_chat_inputbottombar)
    AVInputBottomBar fragmentChatInputbottombar;

    protected MultipleItemAdapter itemAdapter;
    protected AVIMConversation imConversation;
    protected LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_chat, container, false);
        ButterKnife.bind(this, view);
        layoutManager=new LinearLayoutManager(getActivity());
        recylerChat.setLayoutManager(layoutManager);
        refreshChat.setEnabled(false);
        itemAdapter=new MultipleItemAdapter();
        recylerChat.setAdapter(itemAdapter);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        refreshChat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                AVIMMessage message = itemAdapter.getFirstMessage();
//                imConversation.queryMessages(message.getMessageId(), message.getTimestamp(), 20, new AVIMMessagesQueryCallback() {
//                    @Override
//                    public void done(List<AVIMMessage> list, AVIMException e) {
//                        refreshChat.setRefreshing(false);
//                        if (filterException(e)) {
//                            if (null != list && list.size() > 0) {
//                                itemAdapter.addMessageList(list);
//                                itemAdapter.notifyDataSetChanged();
//                                layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
//                            }
//                        }
//                    }
//                });
//            }
//        });
    }

    /**
     * 拉取消息，必须加入 conversation 后才能拉取消息
     */
    private void fetchMessages() {
        imConversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (filterException(e)) {
                    itemAdapter.setMessageList(list);
                    recylerChat.setAdapter(itemAdapter);
                    itemAdapter.notifyDataSetChanged();
                    scrollToBottom();
                }
            }
        });
    }

    public void setImConversation(AVIMConversation imConversation) {
        this.imConversation = imConversation;
        refreshChat.setEnabled(true);
        fragmentChatInputbottombar.setTag(imConversation.getConversationId());
        fetchMessages();
        NotificationUtils.addTag(imConversation.getConversationId());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != imConversation) {
            NotificationUtils.addTag(imConversation.getConversationId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NotificationUtils.removeTag(imConversation.getConversationId());
    }

    /**
     * 输入事件处理，接收后构造成 AVIMTextMessage 然后发送（来着AVInputBottomBar）
     * 因为不排除某些特殊情况会受到其他页面过来的无效消息，所以此处加了 tag 判断
     */
    public void onEvent(InputBottomBarTextEvent textEvent) {
        if (null != imConversation && null != textEvent) {
            if (!TextUtils.isEmpty(textEvent.sendContent) && imConversation.getConversationId().equals(textEvent.tag)) {
                AVIMTextMessage message = new AVIMTextMessage();
                message.setText(textEvent.sendContent);
                itemAdapter.addMessage(message);
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
                imConversation.sendMessage(message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        itemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    /**
     * 处理推送过来的消息（来自于MessageHandler）
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    public void onEvent(ImTypeMessageEvent event) {
        if (null != imConversation && null != event &&
                imConversation.getConversationId().equals(event.conversation.getConversationId())) {
            itemAdapter.addMessage(event.message);
            itemAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    }

    /**
     * 重新发送已经发送失败的消息（来着RightTextHolder）
     */
    public void onEvent(ImTypeMessageResendEvent event) {
        if (null != imConversation && null != event) {
            if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == event.message.getMessageStatus()
                    && imConversation.getConversationId().equals(event.message.getConversationId())) {
                imConversation.sendMessage(event.message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        itemAdapter.notifyDataSetChanged();
                    }
                });
                itemAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    protected void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
