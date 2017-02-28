package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class mydongtai_list {

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
        private String title;
        private String defaultImg;
        private String dynamicId;
        private String eleId;
        private String name;
        private String type;
        private String publishDate;
        private String unitId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDefaultImg() {
            return defaultImg;
        }

        public void setDefaultImg(String defaultImg) {
            this.defaultImg = defaultImg;
        }

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getEleId() {
            return eleId;
        }

        public void setEleId(String eleId) {
            this.eleId = eleId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }
    }
}
