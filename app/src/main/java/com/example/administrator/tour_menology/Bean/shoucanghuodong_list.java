package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class shoucanghuodong_list {

    private int pageCount;
    private boolean success;
    private List<LIstdate> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<LIstdate> getList() {
        return list;
    }

    public void setList(List<LIstdate> list) {
        this.list = list;
    }

    public static  class LIstdate {

        private String activityId;
        private String address;
        private String imgPath;
        private String actDate;
        private String countCollect;
        private String actName;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getActDate() {
            return actDate;
        }

        public void setActDate(String actDate) {
            this.actDate = actDate;
        }

        public String getCountCollect() {
            return countCollect;
        }

        public void setCountCollect(String countCollect) {
            this.countCollect = countCollect;
        }

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }
    }


}
