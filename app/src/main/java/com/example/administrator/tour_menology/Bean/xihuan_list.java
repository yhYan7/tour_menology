package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/27 15:30
 */

public class xihuan_list {
    private String travelContId;
    private String imgPath;
    private String travelId;

    public String getTravelContId() {
        return travelContId;
    }

    public void setTravelContId(String travelContId) {
        this.travelContId = travelContId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public xihuan_list(String travelContId, String imgPath, String travelId) {

        this.travelContId = travelContId;
        this.imgPath = imgPath;
        this.travelId = travelId;
    }
}
