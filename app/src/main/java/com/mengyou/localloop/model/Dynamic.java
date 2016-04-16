package com.mengyou.localloop.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class Dynamic {

    private int iconUrl;
    private List<Integer> dynamicUrls;
    private String dynamic;
    private String time;
    private String username;
    private String level;

    public int getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(int iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public List<Integer> getDynamicUrls() {
        return dynamicUrls;
    }

    public void setDynamicUrls(List<Integer> dynamicUrls) {
        this.dynamicUrls = dynamicUrls;
    }
}
