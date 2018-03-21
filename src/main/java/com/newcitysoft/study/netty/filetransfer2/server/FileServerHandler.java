package com.newcitysoft.study.netty.filetransfer2.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件服务处理类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:31
 */
@ChannelHandler.Sharable
public class FileServerHandler extends ChannelHandlerAdapter {

    private boolean first = true;
    private FileOutputStream fos;
    private BufferedOutputStream bufferedOutputStream;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        first = true;
        System.out.println(ctx.channel().localAddress().toString() + " channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " channelInactive");
        // 关闭流
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        first = false;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        // 第一次接收信息只创建文件
        if (first) {
            System.out.println("创建文件");
            first = false;
            File file = new File("D://test//" + new SimpleDateFormat("yyyymmddhhmmss").format(new Date()) + ".txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fos = new FileOutputStream(file);
                bufferedOutputStream = new BufferedOutputStream(fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }

        // 开始处理文件信息
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("本次接收内容长度：" + msg.toString().length());
        try {
            bufferedOutputStream.write(bytes, 0, bytes.length);
            buf.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}