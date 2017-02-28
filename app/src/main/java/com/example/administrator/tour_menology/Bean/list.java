package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/8 8:58
 */

public class list {

    private String data;
    private String data2;
    private String title;
    private String img;
    private String id;
    private String type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public list(String data, String data2, String title, String img, String id, String type) {
        this.data = data;
        this.data2 = data2;
        this.title = title;
        this.img = img;
        this.id = id;
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
