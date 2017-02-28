package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/9/9 17:36
 */
public class gonglue_list {
    private String logo;
    private String title;
    private String areaName;
    private String days;
    private String publishDate;
    private String strategyId;

    public gonglue_list(String logo, String title, String areaName, String days, String publishDate, String strategyId) {
        this.logo = logo;
        this.title = title;
        this.areaName = areaName;
        this.days = days;
        this.publishDate = publishDate;
        this.strategyId = strategyId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }
}
