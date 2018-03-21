package com.newcitysoft.study.netty.filetransfer.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/20 19:14
 */
public class FileClientHandler extends ChannelHandlerAdapter {

    private static String path = "D:\\data\\socket\\client\\123.txt";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(path);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RandomAccessFile file = new RandomAccessFile("d:\\test\\456.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);

        ByteBuf buf = (ByteBuf) msg;
        byteBuffer.put(buf.getByte(buf.readableBytes()));
        byteBuffer.flip();
        while(byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
        System.out.println(channel.position());
        close(file, channel);
    }

    public static void close(RandomAccessFile file, FileChannel channel) throws IOException {
        channel.close();
        file.close();
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


