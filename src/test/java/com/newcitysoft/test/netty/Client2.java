package com.newcitysoft.test.netty;

import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.work.client.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:14
 */
public class Client2 {
    /** 延迟执行 */
    private static ScheduledExecutorService reportExector = Executors.newScheduledThreadPool(5);
    public static void main(String[] args) {
        final int port = Const.BIO_PORT;
        reportExector.schedule(new Runnable() {
            @Override
            public void run() {
                Plugin.report(Plugin.connect("127.0.0.1", port));
            }
        }, 3, TimeUnit.SECONDS);

    }
}
