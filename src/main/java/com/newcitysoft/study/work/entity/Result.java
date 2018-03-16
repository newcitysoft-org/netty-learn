package com.newcitysoft.study.work.entity;

import java.io.Serializable;

/**
 * 任务相应结果类
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 11:06
 */
public class Result implements Serializable{
    public static class Code {
        /**
         * 数据封装错误
         */
        public static final int ERROR_ENCODE = 100;
        /**
         * 发送空包
         */
        public static final int ERROR_NULL_PACKET = 101;
        /**
         * 发送空包
         */
        public static final int ERROR_NULL_TYPE = 102;
        /**
         * 事件不支持
         */
        public static final int ERROR_EVENT = 103;

        /**
         * 成功码
         */
        public static final int SUCCESS = 200;
    }

    public static final String RESULT_FAILURE = "failure";
    public static final String RESULT_SUCCESS = "success";

    private String result;
    private int code;
    private String description;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result='" + result + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
