package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/17 10:43
 */

public class add_friend_list {
    private String headIcon;
    private String sex;
    private String nickName;
    private String memberId;


    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public add_friend_list(String headIcon, String sex, String nickName, String memberId) {

        this.headIcon = headIcon;
        this.sex = sex;
        this.nickName = nickName;
        this.memberId = memberId;
    }
}
