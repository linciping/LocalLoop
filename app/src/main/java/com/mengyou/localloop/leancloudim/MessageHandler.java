package com.mengyou.localloop.leancloudim;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mengyou.library.Constant;
import com.mengyou.localloop.R;
import com.mengyou.localloop.event.ImTypeMessageEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/25.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    private Context context;

    public MessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        Log.e("message",((AVIMTextMessage)message).getText());
        String clietID="";
        try {
            clietID=AVImClientManager.getInstance().getClientId();
            if (client.getClientId().equals(clietID))
            {
                if (!message.getFrom().equals(clietID))
                {
                    sendMessage(message,conversation);
                    if (NotificationUtils.isShowNotification(conversation.getConversationId())) {
                        sendNotification(message, conversation);
                    }
                }
            }
            else
            {
                client.close(null);
            }
        }
        catch (IllegalStateException e)
        {
            Log.e("error",e.getMessage());
            client.close(null);
        }

    }

    /**
     * 发布消息通知
     * @param message 消息
     * @param conversation 对话
     */
    public void sendMessage(AVIMTypedMessage message,AVIMConversation conversation)
    {
        ImTypeMessageEvent messageEvent=new ImTypeMessageEvent();
        messageEvent.message=message;
        messageEvent.conversation=conversation;
        EventBus.getDefault().post(messageEvent);
    }

    private void sendNotification(AVIMTypedMessage message, AVIMConversation conversation) {
        String notificationContent = message instanceof AVIMTextMessage ?
                ((AVIMTextMessage)message).getText() : context.getString(R.string.unspport_message_type);

        Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
        intent.putExtra(Constant.CONVERSATION_ID, conversation.getConversationId());
        intent.putExtra(Constant.MEMBER_ID, message.getFrom());
        NotificationUtils.showNotification(context, message.getFrom(), notificationContent, null, intent);
    }
}
