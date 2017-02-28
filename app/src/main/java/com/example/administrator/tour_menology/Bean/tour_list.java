package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * name：yhyan
 * date：2016/8/10 14:14
 */
public class tour_list {

    private String errcode;
    private String errmesg;

    private List<listAct> listAct;

    public static  class listAct{
        private String startTime;
        private String activityId;
        private String selfActId;
        private String actDateStr;
        private String address;
        private String poster;
        private String endTime;
        private String actName;

        public String getStartTime()
        {
            return startTime;
        }

        public void setStartTime(String startTime)
        {
            this.startTime = startTime;
        }

        public String getActivityId()
        {
            return activityId;
        }

        public void setActivityId(String activityId)
        {
            this.activityId = activityId;
        }

        public String getSelfActId()
        {
            return selfActId;
        }

        public void setSelfActId(String selfActId)
        {
            this.selfActId = selfActId;
        }

        public String getActDateStr()
        {
            return actDateStr;
        }

        public void setActDateStr(String actDateStr)
        {
            this.actDateStr = actDateStr;
        }

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public String getPoster()
        {
            return poster;
        }

        public void setPoster(String poster)
        {
            this.poster = poster;
        }

        public String getEndTime()
        {
            return endTime;
        }

        public void setEndTime(String endTime)
        {
            this.endTime = endTime;
        }

        public String getActName()
        {
            return actName;
        }

        public void setActName(String actName)
        {
            this.actName = actName;
        }
    }
    private  List<listEvent> mList2;

    public static  class listEvent{

    }
    private List<listTask> listTask;

    public static  class listTask{

    private String taskId;
    private String title;
    private String remindTime;
    private String finishStatus;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemindTime() {
            return remindTime;
        }

        public void setRemindTime(String remindTime) {
            this.remindTime = remindTime;
        }

        public String getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(String finishStatus) {
            this.finishStatus = finishStatus;
        }
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

    public List<listAct> getListAct() {
        return listAct;
    }

    public void setListAct(List<listAct> listAct) {
        listAct = listAct;
    }

    public List<listEvent> getList2() {
        return mList2;
    }

    public void setList2(List<listEvent> list2) {
        mList2 = list2;
    }

    public List<listTask> getListTask() {
        return listTask;
    }

    public void setListTask(List<listTask> listTask) {
        listTask = listTask;
    }
}
