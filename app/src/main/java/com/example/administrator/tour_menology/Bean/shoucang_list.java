package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/11/15 11:07
 */

public class shoucang_list {

    private List<listdata>list;
    private int pageCount;
    private Boolean success;

    public List<listdata> getList() {
        return list;
    }

    public void setList(List<listdata> list) {
        this.list = list;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class listdata{
        private String title;
        private String area;
        private String days;
        private String imgPath;
        private String travelStoreId;
        private String travelId;
        private String createDateStr;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getTravelStoreId() {
            return travelStoreId;
        }

        public void setTravelStoreId(String travelStoreId) {
            this.travelStoreId = travelStoreId;
        }

        public String getTravelId() {
            return travelId;
        }

        public void setTravelId(String travelId) {
            this.travelId = travelId;
        }

        public String getCreateDateStr() {
            return createDateStr;
        }

        public void setCreateDateStr(String createDateStr) {
            this.createDateStr = createDateStr;
        }
    }


}
