package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class myyouji_list {

    private String errcode;
    private String errmesg;
    private int count;
    private int pageCount;
    private List<LIstdate> list;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmesg() {
        return errmesg;
    }

    public void setErrmesg(String errmesg) {
        this.errmesg = errmesg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<LIstdate> getList() {
        return list;
    }

    public void setList(List<LIstdate> list) {
        this.list = list;
    }

    public static  class LIstdate{
        private String startDate;
        private String logo;
        private String title;
        private String browseCount;
        private String areaName;
        private String days;
        private String travelId;
        private String storeCount;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
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

        public String getBrowseCount() {
            return browseCount;
        }

        public void setBrowseCount(String browseCount) {
            this.browseCount = browseCount;
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

        public String getTravelId() {
            return travelId;
        }

        public void setTravelId(String travelId) {
            this.travelId = travelId;
        }

        public String getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(String storeCount) {
            this.storeCount = storeCount;
        }
    }

}
