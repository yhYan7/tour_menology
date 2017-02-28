package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/9/20 15:02
 */
public class zan_list {
    private String headIcon;
    private String memberId;

    public zan_list(String headIcon, String memberId) {
        this.headIcon = headIcon;
        this.memberId = memberId;
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
}
