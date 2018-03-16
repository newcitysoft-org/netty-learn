package com.newcitysoft.study.netty.channel.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import javax.xml.crypto.Data;
import java.net.InetSocketAddress;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/16 17:40
 */
public class DatagramClient {
    public static void main(String[] args) {
        new DatagramClient().bootstrap();
    }

    public void bootstrap() {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(new OioEventLoopGroup())
                 .channel(OioDatagramChannel.class)
                 .handler(new ChannelInitializer<Channel>() {
                     @Override
                     protected void initChannel(Channel socketChannel) throws Exception {
                         socketChannel.pipeline().addLast(new StringEncoder());
                         socketChannel.pipeline().addLast(new StringDecoder());
                         socketChannel.pipeline().addLast(new ChannelHandlerAdapter(){
                             @Override
                             public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                 System.out.println("active");
                                 ctx.writeAndFlush("hello");
                             }

                             @Override
                             public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                 System.out.println("receive:" + msg);
                             }
                         });
                     }
                 });

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(1111));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Channel bound");
                } else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
