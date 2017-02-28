package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class shijian_list {

    private String errcode;
    private String errmesg;
    private List<listAct> listAct;
    private List<listEvent> listEvent;

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

    public List<shijian_list.listAct> getListAct() {
        return listAct;
    }

    public void setListAct(List<shijian_list.listAct> listAct) {
        this.listAct = listAct;
    }

    public List<shijian_list.listEvent> getListEvent() {
        return listEvent;
    }

    public void setListEvent(List<shijian_list.listEvent> listEvent) {
        this.listEvent = listEvent;
    }

    public static  class listAct {

        private String startTime;
        private String activityId;
        private String selfActId;
        private String actDateStr;
        private String address;
        private String poster;
        private int type;
        private String endTime;
        private String actName;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getSelfActId() {
            return selfActId;
        }

        public void setSelfActId(String selfActId) {
            this.selfActId = selfActId;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }
    }

    public static  class listEvent {

        private String startTime;
        private String title;
        private String eventId;
        private String tagIcon;
        private int fullDay;
        private int type;
        private String endTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getTagIcon() {
            return tagIcon;
        }

        public void setTagIcon(String tagIcon) {
            this.tagIcon = tagIcon;
        }

        public int getFullDay() {
            return fullDay;
        }

        public void setFullDay(int fullDay) {
            this.fullDay = fullDay;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }


}
