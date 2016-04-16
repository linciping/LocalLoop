package com.mengyou.localloop.model;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class MessageCache{

    private int id;
    private String conversationId;
    private String couversationName;
    private String createId;
    private List<String> memberId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getMemberId() {
        return memberId;
    }

    public void setMemberId(List<String> memberId) {
        this.memberId = memberId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCouversationName() {
        return couversationName;
    }

    public void setCouversationName(String couversationName) {
        this.couversationName = couversationName;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
