package com.newcitysoft.study.netty.work.entity;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:52
 */
@Deprecated
public class Task {
    private String type;
    private int count;

    public Task(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public Task() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Task{" +
                "type='" + type + '\'' +
                ", count=" + count +
                '}';
    }
}
