package com.newcitysoft.study.netty.channel.encode;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:36
 */
public class ClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler() {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = new User();

        user.setName("tianlixin");
        user.setPassword("123456");

        ctx.writeAndFlush(user);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User user = (User) msg;
        System.out.println(user.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
