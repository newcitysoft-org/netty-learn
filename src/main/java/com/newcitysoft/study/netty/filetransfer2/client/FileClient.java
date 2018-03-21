package com.newcitysoft.study.netty.filetransfer2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 文件客户端引导类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:15
 */
public class FileClient implements Runnable {

    private ChannelFuture f = null;

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap b = new Bootstrap();

            b.group(group);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY, true);
            b.handler(new FileClientInitializer());

            //发起异步链接
            f = b.connect("127.0.0.1", 7397);

            //等待客户端链路关闭
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
