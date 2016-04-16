package com.mengyou.localloop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.mengyou.localloop.leancloudim.AVImClientManager;
import com.mengyou.localloop.model.AVIMMessageObject;
import com.mengyou.localloop.model.Chat;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewholder.ChatLeftViewHolder;
import com.mengyou.localloop.viewholder.ChatRightViewHolder;
import com.mengyou.localloop.viewholder.CommanViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_LEFT_TEXT = 0;
    private final int ITEM_RIGHT_TEXT = 1;
    private Map<String,String> iconMaps=new HashMap<>();//记录用户头像

    // 时间间隔最小为十分钟
    private final long TIME_INTERVAL = 10 * 60 * 1000;

    private List<AVIMMessage> messageList = new ArrayList<AVIMMessage>();


    public void setMessageList(List<AVIMMessage> messages) {
        messageList.clear();
        if (null != messages) {
            messageList.addAll(messages);
        }
    }

    public void addMessageList(List<AVIMMessage> messages) {
        messageList.addAll(0, messages);
    }

    public void addMessage(AVIMMessage message) {
        messageList.addAll(Arrays.asList(message));
    }

    public AVIMMessage getFirstMessage() {
        if (null != messageList && messageList.size() > 0) {
            return messageList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_LEFT_TEXT) {
            return new ChatLeftViewHolder(parent.getContext(), parent);
        } else if (viewType == ITEM_RIGHT_TEXT) {
            return new ChatRightViewHolder(parent.getContext(), parent);
        } else {
            //TODO
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String userId= messageList.get(position).getFrom();//设置头像
        if (!isHaveKey(userId)) {//判断是否包含当前用户
            AVQuery<User> query = User.getQuery(User.class);
            query.whereEqualTo("username", userId);
            query.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> list, AVException e) {
                    User user = list.get(0);
                    iconMaps.put(userId, user.getAVFile("icon").getUrl());
                    ((CommanViewHolder)holder).bindData(messageList.get(position),user.getAVFile("icon").getUrl());
                    if (holder instanceof ChatLeftViewHolder) {
                        ((ChatLeftViewHolder)holder).showTimeView(shouldShowTime(position));
                    } else if (holder instanceof ChatRightViewHolder) {
                        ((ChatRightViewHolder)holder).showTimeView(shouldShowTime(position));
                    }
                }
            });
        }
        else
        {
            ((CommanViewHolder)holder).bindData(messageList.get(position),iconMaps.get(userId));
            if (holder instanceof ChatLeftViewHolder) {
                ((ChatLeftViewHolder)holder).showTimeView(shouldShowTime(position));
            } else if (holder instanceof ChatRightViewHolder) {
                ((ChatRightViewHolder)holder).showTimeView(shouldShowTime(position));
            }
        }
    }

    private boolean isHaveKey(String key)
    {
        Set<String> set=iconMaps.keySet();
        for (String targetKey:set)
        {
            if (targetKey.equals(key))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        AVIMMessage message = messageList.get(position);
        if (message.getFrom().equals(AVImClientManager.getInstance().getClientId())) {
            return ITEM_RIGHT_TEXT;
        } else {
            return ITEM_LEFT_TEXT;
        }
    }

    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = messageList.get(position - 1).getTimestamp();
        long curTime = messageList.get(position).getTimestamp();
        return curTime - lastTime > TIME_INTERVAL;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
