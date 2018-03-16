package com.newcitysoft.study.work.netty.client;

import com.newcitysoft.study.work.common.Const;
import com.newcitysoft.study.work.common.ClientManger;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:29
 */
public class Client {
    private final static String host = "127.0.0.1";
    private final static int port = Const.port;

    private Channel channel = null;

    private static Client instance = new Client();
    private Client(){}
    public static Client getInstance(){ return instance; }

    public void connect(Message message, TaskAsyncExecutor executor) {
        ClientHandler clientHandler = new ClientHandler(message, executor);
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this
                                    .getClass().getClassLoader())));
                            socketChannel.pipeline().addLast(clientHandler);
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            group.shutdownGracefully();
            channel = null;
        } finally {
            group.shutdownGracefully();
            channel = null;
        }
    }

//    private boolean isConnected() {
//        if(channel != null && channel.isRegistered()) {
//            return true;
//        }
//        return false;
//    }
//
//    private void checkAndConn(String content, TaskAsyncExecutor executor) {
//        System.out.println(channel);
//        if(!isConnected()) {
//            connect(content, executor);
//        }
//    }

    public void getTasks(String type, TaskAsyncExecutor executor) {
        Message message = ClientManger.parseGetTaskMessage(MessageType.SYNC_GET, type);
        connect(message, executor);
    }

    public void report(Object result, Map<String, Object> attachment) {
        Message message = ClientManger.parseReport(result, attachment);
        connect(message, null);
////        byte[] req = msg.getBytes();
////        ByteBuf message = Unpooled.buffer(req.length);
////        message.writeBytes(req);
//        ByteBuf buf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
//        channel.writeAndFlush(buf);
    }
}
