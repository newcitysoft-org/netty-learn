package com.newcitysoft.study.work.netty.plugin;

import com.newcitysoft.study.work.netty.client.Client;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/14 13:47
 */
public class App {
    public static void main(String[] args) {
        Client client = Client.getInstance();

        client.connect();
    }
}
