package com.newcitysoft.study.netty.scene.socket.entity;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 10:52
 */
public class SendItem {
    private String taskId;
    private String content;
    private long timestamp;

    public SendItem(String taskId, String content, Long timestamp) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
