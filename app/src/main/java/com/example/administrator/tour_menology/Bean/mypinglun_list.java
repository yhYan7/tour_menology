package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class mypinglun_list {

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
        private String activityId;
        private String content;
        private String actDateStr;
        private String nickName;
        private String posterPath;
        private String imgPath;
        private String memberId;
        private String actName;
        private List<Listdatereply> reply;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getActDateStr() {
            return actDateStr;
        }

        public void setActDateStr(String actDateStr) {
            this.actDateStr = actDateStr;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }

        public static  class Listdatereply{
            private String content;
            private String originalMemberID;
            private String currentMemberID;
            private String originalMembernickName;
            private String currentMembernickName;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOriginalMemberID() {
                return originalMemberID;
            }

            public void setOriginalMemberID(String originalMemberID) {
                this.originalMemberID = originalMemberID;
            }

            public String getCurrentMemberID() {
                return currentMemberID;
            }

            public void setCurrentMemberID(String currentMemberID) {
                this.currentMemberID = currentMemberID;
            }

            public String getOriginalMembernickName() {
                return originalMembernickName;
            }

            public void setOriginalMembernickName(String originalMembernickName) {
                this.originalMembernickName = originalMembernickName;
            }

            public String getCurrentMembernickName() {
                return currentMembernickName;
            }

            public void setCurrentMembernickName(String currentMembernickName) {
                this.currentMembernickName = currentMembernickName;
            }
        }
    }
}
