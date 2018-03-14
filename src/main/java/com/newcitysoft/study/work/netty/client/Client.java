package com.newcitysoft.study.work.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.work.entity.Header;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:29
 */
public class Client {
    EventLoopGroup group = null;

    private final static String host = "127.0.0.1";
    private final static int port = 9090;

    private static Client instance = new Client();
    private Client(){}
    public static Client getInstance(){ return instance; }

    public void connect() {
            // 配置客户端NIO线程组
            group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture future = b.connect(host, port).sync();
//            future.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                    if (future.isSuccess()) {
//                        ByteBuf buffer = Unpooled.copiedBuffer(
//                                "Hello", Charset.defaultCharset());
//                        future.channel().writeAndFlush(buffer);
//                    } else {
//                        Throwable cause = future.cause();
//                        cause.printStackTrace();
//                    }
//                }
//            });
            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            group.shutdownGracefully();
        }
    }

    private boolean isConnected() {
        if(group == null || group.isTerminated() || group.isShutdown()) {
            return false;
        }

        return true;
    }

    private void checkAndConn() {
        if(isConnected()) {
            connect();
        }
    }


    public static void main(String[] args) {
        int port = Const.NETTY_PORT;
        //new Client().connect("127.0.0.1", port);
    }
}
