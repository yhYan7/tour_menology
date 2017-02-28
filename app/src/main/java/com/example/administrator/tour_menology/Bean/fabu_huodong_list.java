package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/8/22 10:31
 */
public class fabu_huodong_list {
    private String errcode;
    private String errmesg;
    private int count;
    private int pageCount;
    private List<list> list;

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

    public List<list> getList() {
        return list;
    }

    public void setList(List<list> list) {
        this.list = list;
    }

    public static  class list{
        private String activityId;
        private String actDateStr;
        private String address;
        private String poster;
        private int collect_count;
        private String actName;
        private boolean collect;

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

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }
    }
}
