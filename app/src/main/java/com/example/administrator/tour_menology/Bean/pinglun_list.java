package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/8/23 14:30
 */
public class pinglun_list {
    private String discussId ;
    private String memberId;
    private String nickName;
    private String headIcon;
    private String content;
    private String time;

    public pinglun_list(String discussId, String memberId, String nickName, String headIcon, String content, String time) {
        this.discussId = discussId;
        this.memberId = memberId;
        this.nickName = nickName;
        this.headIcon = headIcon;
        this.content = content;
        this.time = time;
    }

    public pinglun_list(String content, String time, String headIcon, String nickName) {
        this.content = content;
        this.time = time;
        this.headIcon = headIcon;
        this.nickName = nickName;
    }

    public String getDiscussId() {
        return discussId;
    }

    public void setDiscussId(String discussId) {
        this.discussId = discussId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
