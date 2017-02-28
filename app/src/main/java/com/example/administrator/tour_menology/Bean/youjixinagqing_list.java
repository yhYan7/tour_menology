package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/9/12 16:06
 */
public class youjixinagqing_list {
    private String travelEnjoyId;
    private String travelContId;
    private String address;
    private String countEnjoy;
    private String cont;
    private String imgDesc;
    private String travelType;
    private String travelId;
    private String days;
    private String traveldate;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTraveldate() {
        return traveldate;
    }

    public void setTraveldate(String traveldate) {
        this.traveldate = traveldate;
    }

    public youjixinagqing_list(String travelEnjoyId, String travelContId, String address, String countEnjoy, String cont, String imgDesc, String travelType, String travelId, String days, String traveldate) {
        this.travelEnjoyId = travelEnjoyId;
        this.travelContId = travelContId;
        this.address = address;
        this.countEnjoy = countEnjoy;
        this.cont = cont;
        this.imgDesc = imgDesc;
        this.travelType = travelType;
        this.travelId = travelId;
        this.days = days;
        this.traveldate = traveldate;
    }

    public String getTravelEnjoyId() {
        return travelEnjoyId;
    }

    public void setTravelEnjoyId(String travelEnjoyId) {
        this.travelEnjoyId = travelEnjoyId;
    }

    public String getTravelContId() {
        return travelContId;
    }

    public void setTravelContId(String travelContId) {
        this.travelContId = travelContId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountEnjoy() {
        return countEnjoy;
    }

    public void setCountEnjoy(String countEnjoy) {
        this.countEnjoy = countEnjoy;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public String getTravelId() {
        return travelId;
    }

    public youjixinagqing_list(String travelEnjoyId, String travelContId, String address, String countEnjoy, String cont, String imgDesc, String travelType, String travelId) {
        this.travelEnjoyId = travelEnjoyId;
        this.travelContId = travelContId;
        this.address = address;
        this.countEnjoy = countEnjoy;
        this.cont = cont;
        this.imgDesc = imgDesc;
        this.travelType = travelType;
        this.travelId = travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }
}
