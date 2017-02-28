package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/28 9:50
 */

public class shequ_list {
    private  String countPublish;
    private  String logo;
    private  String countFocus;
    private  String unitName;
    private  String unitId;

    public String getCountPublish() {
        return countPublish;
    }

    public void setCountPublish(String countPublish) {
        this.countPublish = countPublish;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountFocus() {
        return countFocus;
    }

    public void setCountFocus(String countFocus) {
        this.countFocus = countFocus;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public shequ_list(String countPublish, String logo, String countFocus, String unitName, String unitId) {

        this.countPublish = countPublish;
        this.logo = logo;
        this.countFocus = countFocus;
        this.unitName = unitName;
        this.unitId = unitId;
    }
}
