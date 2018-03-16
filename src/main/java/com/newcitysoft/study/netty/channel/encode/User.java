package com.newcitysoft.study.netty.channel.encode;

import java.io.Serializable;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/16 12:25
 */
public class User implements Serializable {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("User[name:%s,password:%s]", this.name, this.password);
    }

    public User() {
    }
}
