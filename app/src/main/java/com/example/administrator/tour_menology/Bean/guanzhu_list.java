package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/28 13:15
 */

public class guanzhu_list {
    private String countAttention;
    private String countFans;
    private String headIcon;
    private String memberId;
    private String nickName;

    public String getCountAttention() {
        return countAttention;
    }

    public void setCountAttention(String countAttention) {
        this.countAttention = countAttention;
    }

    public String getCountFans() {
        return countFans;
    }

    public void setCountFans(String countFans) {
        this.countFans = countFans;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
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

    public guanzhu_list(String countAttention, String countFans, String headIcon, String memberId, String nickName) {

        this.countAttention = countAttention;
        this.countFans = countFans;
        this.headIcon = headIcon;
        this.memberId = memberId;
        this.nickName = nickName;
    }
}
