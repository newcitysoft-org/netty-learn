package com.newcitysoft.study.work.entity;

import java.io.Serializable;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 13:50
 */
public class TaskResult implements Serializable{
    private String taskId;
    private Object result;
    private long getTime;
    private long reportTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getGetTime() {
        return getTime;
    }

    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    public TaskResult(String taskId, Object result, long getTime, long reportTime) {
        this.taskId = taskId;
        this.result = result;
        this.getTime = getTime;
        this.reportTime = reportTime;
    }

    public TaskResult() {
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskId='" + taskId + '\'' +
                ", result=" + result +
                ", getTime=" + getTime +
                ", reportTime=" + reportTime +
                '}';
    }
}
