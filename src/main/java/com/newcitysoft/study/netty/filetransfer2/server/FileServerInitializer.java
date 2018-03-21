package com.newcitysoft.study.netty.filetransfer2.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;


/**
 * 服务端流水线配置类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:29
 */
public class FileServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        System.out.println("报告");
        System.out.println("信息：有一客户端链接到本服务端");
        System.out.println("IP:" + ch.localAddress().getHostName());
        System.out.println("Port:" + ch.localAddress().getPort());
        System.out.println("报告完毕");

        ch.pipeline().addLast(new ByteArrayEncoder());
        // 在管道中添加我们自己的接收数据实现方法
        ch.pipeline().addLast(new FileServerHandler());

    }
}
