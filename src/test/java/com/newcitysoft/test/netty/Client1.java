package com.newcitysoft.test.netty;

import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.work.client.Client;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:13
 */
public class Client1 {
    public static void main(String[] args) {
        final int port = Const.BIO_PORT;
        final String host = "127.0.0.1";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Client.report(Client.connect(host, port));
            }
        }).start();

        Client.communicate(Client.connect(host, port));
    }
}
