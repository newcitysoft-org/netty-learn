package com.newcitysoft.study.netty.scene.socket.protocol;

import java.io.Serializable;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/7 11:44
 */
public abstract class BaseEntity implements Serializable{

    private String msgType;

    public BaseEntity(String msgType) {
        this.msgType = msgType;
    }

    public BaseMessage packData() {
        BaseMessage baseMessage = new BaseMessage(this.msgType, this.toString());
        return  baseMessage;
    }
}
