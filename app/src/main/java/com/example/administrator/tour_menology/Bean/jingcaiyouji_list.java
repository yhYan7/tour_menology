package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/7 9:14
 */
public class jingcaiyouji_list {

    private String logo;
    private String headIcon;
    private String title;
    private String nickNane;
    private String travelId;
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickNane() {
        return nickNane;
    }

    public void setNickNane(String nickNane) {
        this.nickNane = nickNane;
    }

    public jingcaiyouji_list(String logo, String headIcon, String title, String nickNane, String travelId, String memberId) {
        this.logo = logo;
        this.headIcon = headIcon;
        this.title = title;
        this.nickNane = nickNane;
        this.travelId = travelId;
        this.memberId = memberId;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }
}
