package com.newcitysoft.study.netty.filetransfer2.client.common;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import java.io.RandomAccessFile;

/**
 * 文件控制类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-21 14:20
 */
public class FileControl {

    private ChannelHandlerContext channel;

    public FileControl(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    /**
     * 通知服务端我要发送一个文件了
     * 里面的信息你可以第一为自己想要信息格式，包括文件名，长度，类型等
     * 这里只简单的通知文件名
     */
    public void sendNotice(){
        channel.writeAndFlush("00".getBytes(CharsetUtil.UTF_8));
    }

    /**
     * 传送文件
     * @param fileUrl
     */
    public void sendFile(String fileUrl) {
        // 开始执行
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileUrl, "rw");
            ChunkedFile chunkedFile = new ChunkedFile(raf);
            channel.writeAndFlush(chunkedFile).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    future.channel().close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
