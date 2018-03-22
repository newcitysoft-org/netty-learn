package com.newcitysoft.study.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/22 10:47
 */
public class MultiEvent {
    public static void main(String[] args) {
        new MultiEvent().connect(5555);
    }

    private void connect(int port) {
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(boos, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler())
                    .childHandler(new ChildHandlerInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            scheduleFixedViaEventLoop(boos);

            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 定时调度执行
     * @param eventLoop
     */
    private static void scheduleFixedViaEventLoop(EventLoopGroup eventLoop) {
        ScheduledFuture<?> future = eventLoop.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Run every 60 seconds");
                    }
                }, 60, 60, TimeUnit.SECONDS);
    }

    private static class ChildHandlerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();

            pipeline.addFirst(new EventHandler());
        }
    }

    private static class EventHandler extends ChannelHandlerAdapter {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            Channel ch = ctx.channel();
            // 定时任务调度
            ScheduledFuture<?> future = ch.eventLoop().schedule(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("60 seconds later");
                        }
                    }, 60, TimeUnit.SECONDS);
            // 定时周期性任务调度
            ScheduledFuture<?> future2 = ch.eventLoop().scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Run every 60 seconds");
                        }
                    }, 60, 60, TimeUnit.SECONDS);

            // 取消任务调度
            boolean mayInterruptIfRunning = false;
            future.cancel(mayInterruptIfRunning);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            super.channelReadComplete(ctx);
        }
    }
}
