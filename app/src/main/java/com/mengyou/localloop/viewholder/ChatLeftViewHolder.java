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
import com.mengyou.localloop.event.LeftChatItemClickEvent;
import com.mengyou.localloop.model.AVIMMessageObject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/25.
 */
public class ChatLeftViewHolder extends CommanViewHolder {
    @Bind(R.id.chat_left_text_tv_time)
    TextView chatLeftTextTvTime;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.chat_left_text_tv_content)
    TextView chatLeftTextTvContent;
    @Bind(R.id.chat_left_text_progressbar)
    ProgressBar chatLeftTextProgressbar;
    @Bind(R.id.chat_left_text_tv_status)
    ImageView chatLeftTextTvStatus;

    private String userid;
    private  AVIMMessage message;

    public ChatLeftViewHolder(Context mContext, ViewGroup parent) {
        super(mContext, parent, R.layout.item_chat_text_left);
    }

    @OnClick(R.id.icon)
    public void iconOnClick(View view)
    {
        LeftChatItemClickEvent event=new LeftChatItemClickEvent();
        event.userId= userid;
        EventBus.getDefault().post(event);
    }

    @Override
    public void bindData(Object object,String url) {
        message = (AVIMMessage) object;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getTimestamp());

        String content = getContext().getString(R.string.unspport_message_type);
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage) message).getText();
        }
        userid=message.getFrom();
        chatLeftTextTvContent.setText(content);
        chatLeftTextTvTime.setText(time);
        ImageLoader.getInstance().displayImage(url,icon,MyImageLoader.newOption());
    }

    public void showTimeView(boolean isShow) {
        chatLeftTextTvTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
