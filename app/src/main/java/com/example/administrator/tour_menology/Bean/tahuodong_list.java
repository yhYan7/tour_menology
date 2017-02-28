package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/31 10:06
 */

public class tahuodong_list {
    private String activityId;
    private String address;
    private String imgPath;
    private String actDate;
    private String countCollect;
    private String actName;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getActDate() {
        return actDate;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public String getCountCollect() {
        return countCollect;
    }

    public void setCountCollect(String countCollect) {
        this.countCollect = countCollect;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public tahuodong_list(String activityId, String address, String imgPath, String actDate, String countCollect, String actName) {

        this.activityId = activityId;
        this.address = address;
        this.imgPath = imgPath;
        this.actDate = actDate;
        this.countCollect = countCollect;
        this.actName = actName;
    }
}
