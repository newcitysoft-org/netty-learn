package com.newcitysoft.test.netty;

import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.work.client.Plugin;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:13
 */
public class Client1 {
    public static void main(String[] args) {
        final int port = Const.BIO_PORT;
        Plugin.communicate(Plugin.connect("127.0.0.1", port));
    }
}
