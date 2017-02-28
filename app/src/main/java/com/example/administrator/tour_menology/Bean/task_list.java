package com.example.administrator.tour_menology.Bean;

import java.util.List;

/**
 * 任务list
 * Created by Administrator on 2016/11/16 0016.
 */
public class task_list {

    private String errcode;
    private String errmesg;
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

    public List<task_list.list> getList() {
        return list;
    }

    public void setList(List<task_list.list> list) {
        this.list = list;
    }

    public static  class list {
        private String taskId;
        private String title;
        private String remindTime;
        private int finishStatus;

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

        public int getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(int finishStatus) {
            this.finishStatus = finishStatus;
        }
    }


}
