package com.newcitysoft.study.netty.work.entity;

/**
 * 包类型枚举类
 * @author lixin.tian@renren-inc.com
 */
@Deprecated
public enum PacketType {
    /**
     * 同步获取
     */
    SYNCGET("syncGet"),
    /**
     * 异步获取
     */
    ASYNCGET("asyncGet"),
    /**
     * 获取任务
     */
    REPORT("report"),
    /**
     * 发送
     */
    SEND("send"),
    /**
     * 配置
     */
    CONFIG("config"),
    /**
     * 响应
     */
    RESPONSE("response");

    public static boolean contains(String type){
        for(PacketType typeEnum : PacketType.values()){
            if(typeEnum.name().equals(type)){
                return true;
            }
        }
        return false;
    }

    private String type;
    private PacketType(String type) {
        this.type = type;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param typeName 类型名称
     */
    public static PacketType fromTypeName(String typeName) {
        for (PacketType type : PacketType.values()) {
            if (type.getType().equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PacketType{" +
                "type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return this.type;
    }

    public static void main(String[] args) {
        System.out.println(contains("aa".toUpperCase()));
    }
}