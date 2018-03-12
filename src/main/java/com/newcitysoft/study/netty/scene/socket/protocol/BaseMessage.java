package com.newcitysoft.study.netty.scene.socket.protocol;

import java.util.Date;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/7 11:40
 */
public class BaseMessage {
    private String toUserId;
    private String fromUserId;
    private String msgType;
    private long createTime;
    private String content;

    public BaseMessage(String msgType, String content) {
        this.msgType = msgType;
        this.content = content;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "toUserId='" + toUserId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}
