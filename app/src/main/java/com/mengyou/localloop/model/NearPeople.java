package com.mengyou.localloop.model;

/**
 * Created by Administrator on 2016/3/8.
 */
public class NearPeople {

    private int iconUrl;
    private int sexIconUrl;
    private String username;
    private String description;
    private String distance;
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(int iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSexIconUrl() {
        return sexIconUrl;
    }

    public void setSexIconUrl(int sexIconUrl) {
        this.sexIconUrl = sexIconUrl;
    }
}
