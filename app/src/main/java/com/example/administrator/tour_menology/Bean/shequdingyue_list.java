package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class shequdingyue_list {

    private int pageCount;
    private String errcode;
    private String errmesg;
    private List<LIstdate> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

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

    public List<LIstdate> getList() {
        return list;
    }

    public void setList(List<LIstdate> list) {
        this.list = list;
    }

    public static  class LIstdate{
        private String unitId;
        private String unitName;
        private String countFocus;
        private String logo;
        private String countPublish;

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
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

        public String getCountPublish() {
            return countPublish;
        }

        public void setCountPublish(String countPublish) {
            this.countPublish = countPublish;
        }
    }

}
