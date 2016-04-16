package com.mengyou.localloop.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.R;
import com.mengyou.localloop.event.ImTypeMessageResendEvent;
import com.mengyou.localloop.model.AVIMMessageObject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/25.
 */
public class ChatRightViewHolder extends CommanViewHolder {

    @Bind(R.id.chat_right_text_tv_time)
    TextView chatRightTextTvTime;
    @Bind(R.id.chat_right_text_progressbar)
    ProgressBar chatRightTextProgressbar;
    @Bind(R.id.chat_right_text_tv_error)
    ImageView chatRightTextTvError;
    @Bind(R.id.chat_right_text_layout_status)
    FrameLayout chatRightTextLayoutStatus;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.chat_right_text_tv_content)
    TextView chatRightTextTvContent;
    @Bind(R.id.chat_right_text_layout_content)
    LinearLayout chatRightTextLayoutContent;
    private AVIMMessage message;

    public ChatRightViewHolder(Context mContext, ViewGroup parent) {
        super(mContext, parent, R.layout.item_chat_text_right);
    }

    @OnClick(R.id.chat_right_text_tv_error)
    public void errorOnClick(View view)
    {
        ImTypeMessageResendEvent event = new ImTypeMessageResendEvent();
        event.message =message;
        EventBus.getDefault().post(event);
    }

    @Override
    public void bindData(Object object,String url) {
        AVIMMessage message= (AVIMMessage) object;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = dateFormat.format(message.getTimestamp());

        String content = getContext().getString(R.string.unspport_message_type);;
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage)message).getText();
        }

        chatRightTextTvContent.setText(content);
        chatRightTextTvTime.setText(time);
        ImageLoader.getInstance().displayImage(url,icon,MyImageLoader.newOption());

        if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == message.getMessageStatus()) {
            chatRightTextTvError.setVisibility(View.VISIBLE);
            chatRightTextProgressbar.setVisibility(View.GONE);
            chatRightTextLayoutStatus.setVisibility(View.VISIBLE);
        } else if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusSending == message.getMessageStatus()) {
            chatRightTextTvError.setVisibility(View.GONE);
            chatRightTextProgressbar.setVisibility(View.VISIBLE);
            chatRightTextLayoutStatus.setVisibility(View.VISIBLE);
        } else {
            chatRightTextLayoutStatus.setVisibility(View.GONE);
        }
    }

    public void showTimeView(boolean isShow) {
        chatRightTextTvTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
