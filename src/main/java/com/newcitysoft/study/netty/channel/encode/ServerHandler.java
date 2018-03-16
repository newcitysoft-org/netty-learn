package com.newcitysoft.study.netty.channel.encode;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:16
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private static User newUser = new User();

    static {
        newUser.setName("xiaohuan");
        newUser.setPassword("123456");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User user = (User) msg;
        System.out.println(user.toString());
        ctx.writeAndFlush(newUser);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
