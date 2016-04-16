package com.mengyou.localloop.model;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
@AVClassName("Activity")
public class AVActivity extends AVObject {

    private User creater;
    private String time;
    private int limitRank;
    private int whoBuy;
    private int peopleNum;
    private int state;
    private int theme;
    private int objectSex;
    private String contentStr;
    private boolean hideName;
    private String addr;
    private List<AVObject> files;

    public AVActivity()
    {
        super();
    }

    public AVActivity(Parcel in)
    {
        super(in);
    }

    public AVActivity(User creater, String time, int limitRank, int whoBuy,
                      int peopleNum, int state, int theme, int objectSex,
                      boolean hideName, String contentStr, String addr, List<AVObject> files) {
        super();
        this.creater = creater;
        this.time = time;
        this.limitRank = limitRank;
        this.whoBuy = whoBuy;
        this.peopleNum = peopleNum;
        this.state = state;
        this.theme = theme;
        this.objectSex = objectSex;
        this.hideName = hideName;
        this.contentStr = contentStr;
        this.addr = addr;
        this.files = files;
    }

    public AVActivity(Parcel in, String time, int limitRank,
                      int whoBuy, int peopleNum, int state, int theme,
                      int objectSex, String contentStr, boolean hideName, String addr) {
        super(in);
        this.time = time;
        this.limitRank = limitRank;
        this.whoBuy = whoBuy;
        this.peopleNum = peopleNum;
        this.state = state;
        this.theme = theme;
        this.objectSex = objectSex;
        this.contentStr = contentStr;
        this.hideName = hideName;
        this.addr = addr;
    }

    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;


    public List<AVObject> getFiles() {
        return files;
    }

    public void setFiles(List<AVObject> files) {
        this.files = files;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        put("creater",creater);
    }

    public String getTime() {
        return getString("time");
    }

    public void setTime(String time) {
        put("time", time);
    }

    public int getLimitRank() {
        return getInt("limitRank");
    }

    public void setLimitRank(int limitRank) {
        put("limitRank", limitRank);
    }

    public int getWhoBuy() {
        return getInt("whoBuy");
    }

    public void setWhoBuy(int whoBuy) {
        put("whoBuy", whoBuy);
    }

    public int getPeopleNum() {
        return getInt("peopleNum");
    }

    public void setPeopleNum(int peopleNum) {
        put("peopleNum", peopleNum);
    }

    public int getState() {
        return getInt("state");
    }

    public void setState(int state) {
        put("state", state);
    }

    public int getTheme() {
        return getInt("theme");
    }

    public void setTheme(int theme) {
        put("theme", theme);
    }
    public int getObjectSex() {
        return getInt("objectSex");
    }

    public void setObjectSex(int objectSex) {
        put("objectSex", objectSex);
    }

    public String getContentStr() {
        return getString("contentStr");
    }

    public void setContentStr(String contentStr) {
        put("contentStr", contentStr);
    }

    public boolean isHideName() {
        return getBoolean("hideName");
    }

    public void setHideName(boolean hideName) {
        put("hideName", isHideName());
    }

    public String getAddr() {
        return getString("addr");
    }

    public void setAddr(String addr) {
        put("addr", addr);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
