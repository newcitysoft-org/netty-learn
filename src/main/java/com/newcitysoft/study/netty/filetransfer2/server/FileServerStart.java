package com.newcitysoft.study.netty.filetransfer2.server;

/**
 * 文件传输服务端启动类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:24
 */
public class FileServerStart {
    public static void main(String[] args) {
        System.out.println("启动NettyServer start... ...");
        Thread thread = new Thread(new FileServer());
        thread.start();
        System.out.println("启动NettyServer end... ...");
    }
}
