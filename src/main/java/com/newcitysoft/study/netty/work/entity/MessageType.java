package com.newcitysoft.study.netty.work.entity;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/13 16:44
 */
public enum  MessageType {

    SYNC_GET((byte) 0),
    ASYNC_GET((byte) 1),
    REPORT((byte) 2),
    SEND((byte) 3),
    CONFIG((byte) 4),
    RESPONSE((byte) 5);

    private byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }

    public static boolean contains(int value){
        for(MessageType type : MessageType.values()){
            if(type.value() == value){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     */
    public static MessageType fromTypeName(int value) {
        for (MessageType type : MessageType.values()) {
            if (type.value() == value) {
                return type;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(contains(1));
        System.out.println(fromTypeName(2));
    }
}
