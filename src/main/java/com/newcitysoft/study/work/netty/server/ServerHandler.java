package com.newcitysoft.study.work.netty.server;

import com.newcitysoft.study.work.common.Const;
import com.newcitysoft.study.work.common.ServerManager;
import com.newcitysoft.study.work.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:16
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        future.cancel(false);
    }

    ScheduledFuture<?> future = null;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println(message.toString());
        ctx.writeAndFlush(ServerManager.handle(message));

        future = ctx.channel().eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("再次发送");
                ctx.writeAndFlush(ServerManager.handle(message));
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
