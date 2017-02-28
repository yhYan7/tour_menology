package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/2 14:21
 */

public class rili_list {
    private String actId;
    private String actName;
    private String endTime;
    private String poster;
    private String startTime;


    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public rili_list(String actId, String actName, String endTime, String poster, String startTime) {

        this.actId = actId;
        this.actName = actName;
        this.endTime = endTime;
        this.poster = poster;
        this.startTime = startTime;
    }
}
