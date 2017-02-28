package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/14 14:41
 */

public class pl_youji_list {
    private String content;
    private String startDate;
    private String title;
    private String travelContId;
    private String areaName;
    private String days;
    private String nickName;
    private String posterPath;
    private String imgPath;
    private String memberId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTravelContId() {
        return travelContId;
    }

    public void setTravelContId(String travelContId) {
        this.travelContId = travelContId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public pl_youji_list(String content, String startDate, String title, String travelContId, String areaName, String days, String nickName, String posterPath, String imgPath, String memberId) {

        this.content = content;
        this.startDate = startDate;
        this.title = title;
        this.travelContId = travelContId;
        this.areaName = areaName;
        this.days = days;
        this.nickName = nickName;
        this.posterPath = posterPath;
        this.imgPath = imgPath;
        this.memberId = memberId;
    }
}
