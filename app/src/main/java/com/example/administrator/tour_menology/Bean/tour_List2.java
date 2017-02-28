package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/9/5 9:52
 */
public class tour_List2 {
    private String actName;
    private String endTime;
    private String poster;
    private String activityId;

    public tour_List2(String actName, String endTime, String poster) {
        this.actName = actName;
        this.endTime = endTime;
        this.poster = poster;

    }


    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }




}
