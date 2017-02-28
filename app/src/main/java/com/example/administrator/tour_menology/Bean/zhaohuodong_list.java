package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/8/24 15:17
 */
public class zhaohuodong_list {
    private String errcode;
    private String errmesg;
    private int count;
    private int pageCount;
    private List<Listdate> list;

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

    public List<Listdate> getList() {
        return list;
    }

    public void setList(List<Listdate> list) {
        this.list = list;
    }

    public static  class Listdate {
        private String activityId;
        private String actDateStr;
        private String address;
        private String poster;
        private String collect_count;
        private String actName;
        private Boolean collect;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActDateStr() {
            return actDateStr;
        }

        public void setActDateStr(String actDateStr) {
            this.actDateStr = actDateStr;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(String collect_count) {
            this.collect_count = collect_count;
        }

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }

        public Boolean getCollect() {
            return collect;
        }

        public void setCollect(Boolean collect) {
            this.collect = collect;
        }
    }
}
