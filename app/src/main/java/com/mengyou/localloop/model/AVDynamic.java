package com.mengyou.localloop.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2016/3/14.
 */
@AVClassName("Dynamic")
public class AVDynamic extends AVObject {

    private String contentStr;

    public String getContentStr() {
        return getString("contentStr");
    }

    public void setContentStr(String contentStr) {
        put("contentStr",contentStr);
    }
}
