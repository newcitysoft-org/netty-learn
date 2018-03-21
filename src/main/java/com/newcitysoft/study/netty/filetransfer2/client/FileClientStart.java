package com.newcitysoft.study.netty.filetransfer2.client;

import com.newcitysoft.study.netty.filetransfer2.client.common.FileControl;
import com.newcitysoft.study.netty.filetransfer2.client.common.Globle;

import java.util.Date;

/**
 * 客户端启动类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:19
 */
public class FileClientStart {

    public static void main(String[] args) {
        System.out.println("启动NettyClient start ... ...");
        Thread thread = new Thread(new FileClient());
        thread.start();
        System.out.println("启动NettyClient end ... ...");

        // 当socket链接上才可以进行传送文件
        while (null == Globle.channel) {
            try {
                Thread.sleep(1000);
                System.out.println("等待socket握手... ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("发送文件 开始" + new Date());
        String fileUrl = "D:\\data\\test\\456.txt";
        FileControl fileControl = new FileControl(Globle.channel);
        // 通知服务端我要发文件了
        fileControl.sendNotice();
        // 开始发文件
        fileControl.sendFile(fileUrl);
        System.out.println("发送文件 结束" + new Date());
    }

}
