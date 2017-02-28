package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/7/27 10:20
 */
public class main_list1_list {
    private String activityId;
    private String actDateStr;
    private String address;
    private String poster;
    private String collect_count;
    private String actName;
    private Boolean collect;

    public main_list1_list(String activityId, String actDateStr, String address, String poster, String  collect_count, String actName, Boolean collect) {
        this.activityId = activityId;
        this.actDateStr = actDateStr;
        this.address = address;
        this.poster = poster;
        this.collect_count = collect_count;
        this.actName = actName;
        this.collect = collect;
    }

    public main_list1_list(String activityId, String actDateStr, String address, String poster, String collect_count, String actName) {
        this.activityId = activityId;
        this.actDateStr = actDateStr;
        this.address = address;
        this.poster = poster;
        this.collect_count = collect_count;
        this.actName = actName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActDateStr() {
        return actDateStr;
    }

    public void setActDateStr(String actDateStr) {
        this.actDateStr = actDateStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String  getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }
}
