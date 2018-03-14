package com.newcitysoft.study.work.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Socket协议栈消息头
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/13 11:08
 */
public class Header {
    private int length;
    private int sessionID;
    private byte type;
    private byte priority;
    private Map<String, Object> attachment = new HashMap<>();

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public Header() {
    }

    @Override
    public String toString() {
        return "Header{" +
                "length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
