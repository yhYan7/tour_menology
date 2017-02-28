package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * 我的粉丝
 * Created by zkx on 2016/9/27 0027.
 */
public class myfans_list {

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

        private String memberId;
        private String nickName;
        private String headIcon;
        private String attentionCount;
        private String fanscount;
        private String attentionId;
        private  String logo;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadIcon() {
            return headIcon;
        }

        public void setHeadIcon(String headIcon) {
            this.headIcon = headIcon;
        }

        public String getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(String attentionCount) {
            this.attentionCount = attentionCount;
        }

        public String getFanscount() {
            return fanscount;
        }

        public void setFanscount(String fanscount) {
            this.fanscount = fanscount;
        }

        public String getAttentionId() {
            return attentionId;
        }

        public void setAttentionId(String attentionId) {
            this.attentionId = attentionId;
        }
    }

    }
