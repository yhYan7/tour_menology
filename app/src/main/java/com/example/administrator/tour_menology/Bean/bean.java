package com.example.administrator.tour_menology.Bean;

/**
 * Created by yan on 2016/8/24.
 */
public class bean {
    private String areaName;
    private String areaId;

    public bean(String areaName, String areaId) {
        this.areaName = areaName;
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return areaName;
    }
}
