package com.newcitysoft.study.netty.work.entity;

/**
 * Socket数据传输协议包
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 10:42
 */
public class DataPacket<T> {

    private PacketType type;
    private T body;

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "type=" + type +
                ", body=" + body +
                '}';
    }
}
