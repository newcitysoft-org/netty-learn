package com.newcitysoft.test.netty;

import src.file.FileServer;

/**
 * Created by Administrator on 2015/8/25.
 */
public class TestServer {

    public static void main(String[] args) {
        FileServer server = new FileServer(8000, "d:/data/netty");
        server.start();
    }

}
