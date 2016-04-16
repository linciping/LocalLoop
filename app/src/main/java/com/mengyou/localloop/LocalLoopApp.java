package com.mengyou.localloop;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mengyou.library.Constant;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.leancloudim.MessageHandler;
import com.mengyou.localloop.model.AVActivity;
import com.mengyou.localloop.model.AVDynamic;
import com.mengyou.localloop.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/3/3.
 */
public class LocalLoopApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(MyImageLoader.newConfig(this));
        AVUser.alwaysUseSubUserClass(User.class);
        AVObject.registerSubclass(AVActivity.class);
        AVObject.registerSubclass(AVDynamic.class);
        AVOSCloud.initialize(this, Constant.Config.APPID, Constant.Config.APPKEY);
        AVOSCloud.setLastModifyEnabled(true);
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class,new MessageHandler(this));
    }
}
