package com.mengyou.localloop.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class Dymaic {

    private List<Integer> imageUrls;
    private String content;
    private DateModel time;
    private String dymaicId;
    private boolean isShowTime;


    public String getDymaicId() {
        return dymaicId;
    }

    public void setDymaicId(String dymaicId) {
        this.dymaicId = dymaicId;
    }

    public List<Integer> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<Integer> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public DateModel getTime() {
        return time;
    }

    public void setTime(DateModel time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowTime() {
        return isShowTime;
    }

    public void setShowTime(boolean showTime) {
        isShowTime = showTime;
    }
}
