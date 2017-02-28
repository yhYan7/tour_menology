package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/11 15:55
 */

public class jinfenjl_list {
    private String point;
    private String moduleName;
    private String createDateStr;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public jinfenjl_list(String point, String moduleName, String createDateStr) {

        this.point = point;
        this.moduleName = moduleName;
        this.createDateStr = createDateStr;
    }
}
