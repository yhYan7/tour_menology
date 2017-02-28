package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/9/21 14:37
 */
public class all_youji_list {

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
    public static  class list {

        private String logo;
        private String headIcon;
        private String title;
        private String nickName;
        private String travelId;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getHeadIcon() {
            return headIcon;
        }

        public void setHeadIcon(String headIcon) {
            this.headIcon = headIcon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTravelId() {
            return travelId;
        }

        public void setTravelId(String travelId) {
            this.travelId = travelId;
        }
    }
}
