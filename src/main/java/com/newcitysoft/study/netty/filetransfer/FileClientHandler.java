package com.newcitysoft.study.netty.filetransfer;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/20 19:14
 */
public class FileClientHandler extends ChannelHandlerAdapter {

    private final File file;

    public FileClientHandler(File file) {
        this.file = file;
    }

    private static String path = "D:\\data\\socket\\client\\123.txt";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(path);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        //ctx.writeAndFlush(path);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }
}


