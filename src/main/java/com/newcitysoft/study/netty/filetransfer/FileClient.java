package com.newcitysoft.study.netty.filetransfer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.net.InetSocketAddress;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/20 19:19
 */
public class FileClient {

    private static final File file = new File("D:\\data\\socket\\client\\123.txt");

    public void connect(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new FileClientInitializer(file));
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new FileClient().connect(Const.HOST, Const.PORT);
    }
}
