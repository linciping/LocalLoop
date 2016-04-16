package com.mengyou.localloop.model;

/**
 * Created by Administrator on 2016/3/8.
 */
public class Activity {
    private int iconUrl;
    private String username;
    private int sexUrl;
    private String level;
    private String state;
    private int styleUrl;
    private String name;
    private String description="";
    private String time="";
    private String where="";
    private int pictureUrl;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(int iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getSexUrl() {
        return sexUrl;
    }

    public void setSexUrl(int sexUrl) {
        this.sexUrl = sexUrl;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStyleUrl() {
        return styleUrl;
    }

    public void setStyleUrl(int styleUrl) {
        this.styleUrl = styleUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public int getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(int pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
