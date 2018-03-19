package com.newcitysoft.study.netty.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/19 10:51
 */
public class ByteToCharDecoder extends ByteToMessageDecoder implements ChannelInboundHandler {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()>=2) {
            list.add(byteBuf.readChar());
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }
}
