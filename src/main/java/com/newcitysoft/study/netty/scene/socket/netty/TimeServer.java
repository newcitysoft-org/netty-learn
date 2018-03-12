package com.newcitysoft.study.netty.scene.socket.netty;

import com.newcitysoft.study.netty.scene.socket.Const;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:06
 */
public class TimeServer {

    public void bind(int port) {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            // 绑定端口
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // 优雅退出
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
            // 解决TCP粘包导致的读半包或者多包问题的原因
            // 换行符
//            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            // 特殊分隔符
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
//            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        new TimeServer().bind(Const.NETTY_PORT);
    }
}
