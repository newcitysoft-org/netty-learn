package com.newcitysoft.study.work.entity;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 10:52
 */
public class TaskItem {
    private String taskId;
    private Object content;
    private long timestamp;

    public TaskItem() {
    }

    public TaskItem(String taskId, String content, Long timestamp) {
        this.taskId = taskId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SendItem{" +
                "taskId='" + taskId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
