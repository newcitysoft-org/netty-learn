package com.newcitysoft.study.work.netty.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/16 10:56
 */
public class ClientOutboundHandler extends ChannelHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                System.out.println("aaa");
                if (!f.isSuccess()) {
                    f.cause().printStackTrace();
                    f.channel().close();
                } else {
                    System.out.println("操作成功！");
                }
            }
        });
    }
}
