package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/7/30 13:52
 */
public class main_fabu_list {
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
        private String publish_count;
        private  String logo;
        private  String unitName;
        private boolean focus;
        private String unitId;
        private String focus_count;

        public String  getPublish_count() {
            return publish_count;
        }

        public void setPublish_count(String publish_count) {
            this.publish_count = publish_count;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public boolean isFocus() {
            return focus;
        }

        public void setFocus(boolean focus) {
            this.focus = focus;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String  getFocus_count() {
            return focus_count;
        }

        public void setFocus_count(String focus_count) {
            this.focus_count = focus_count;
        }
    }

}
