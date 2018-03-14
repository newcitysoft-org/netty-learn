package com.newcitysoft.test.netty;

import com.newcitysoft.study.netty.work.socket.client.Client;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:13
 */
public class Client1 {
    public static void main(String[] args) {
        Client client = Client.getInstance();

        String tasks = client.getTasks("md5");

        System.out.println(tasks);
    }
}
