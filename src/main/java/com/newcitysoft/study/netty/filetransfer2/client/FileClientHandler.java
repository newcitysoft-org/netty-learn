package com.newcitysoft.study.netty.filetransfer2.client;

import com.newcitysoft.study.netty.filetransfer2.client.common.Globle;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * 文件客户端处理器
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:15
 */
public class FileClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Globle.channel = ctx;
        System.out.println("客户端与服务端通道-开启：" + ctx.channel().localAddress() + "channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Globle.channel = null;
        System.out.println("客户端与服务端通道-关闭：" + ctx.channel().localAddress() + "channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println(ctx.channel().id() + "" + new Date() + " " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Globle.channel = null;
        ctx.close();
        System.out.println("异常退出:" + cause.getMessage());
    }

}
