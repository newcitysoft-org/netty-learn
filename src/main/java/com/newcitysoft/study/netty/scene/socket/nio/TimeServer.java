package com.newcitysoft.study.netty.scene.socket.nio;

import com.newcitysoft.study.zookeeper.module.net.scene.socket.Const;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 16:50
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = Const.BIO_PORT;
        if(args!=null && args.length>0) {
            try {
                port = Integer.parseInt(args[0]);
            }catch (Exception e) {

            }
        }

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
