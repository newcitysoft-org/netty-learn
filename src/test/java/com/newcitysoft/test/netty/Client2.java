package com.newcitysoft.test.netty;

import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.work.socket.client.Client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:14
 */
public class Client2 {
    /** 延迟执行 */
    private static ScheduledExecutorService reportExector = Executors.newScheduledThreadPool(5);
    public static void main(String[] args) {
        final int port = Const.BIO_PORT;
//        reportExector.schedule(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 3, TimeUnit.SECONDS);

        //Client.report(Client.connect("127.0.0.1", port));
    }
}
