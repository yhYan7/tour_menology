package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/20 9:49
 */

public class duihuan_list {
    private String integral;
    private String name;
    private String poster;
    private String recordId;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public duihuan_list(String integral, String name, String poster, String recordId, String date) {

        this.integral = integral;
        this.name = name;
        this.poster = poster;
        this.recordId = recordId;
        this.date = date;
    }
}
