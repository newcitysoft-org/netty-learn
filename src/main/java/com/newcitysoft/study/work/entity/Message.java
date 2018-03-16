package com.newcitysoft.study.work.entity;

import java.io.Serializable;

/**
 * Socket数据传输协议包
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 10:42
 */
public class Message implements Serializable{

    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public Message() {
    }
}
