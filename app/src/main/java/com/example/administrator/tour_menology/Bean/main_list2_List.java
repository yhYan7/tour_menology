package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/7/26 13:40
 */
public class main_list2_List  {
    private String newsId;
    private String title;
    private String pubTime;
    private String source;

    public main_list2_List(String newsId, String title, String pubTime, String source) {
        this.newsId = newsId;
        this.title = title;
        this.pubTime = pubTime;
        this.source = source;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
