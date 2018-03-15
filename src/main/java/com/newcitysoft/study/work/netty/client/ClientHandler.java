package com.newcitysoft.study.work.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.entity.Header;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:36
 */
public class ClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    private byte[] req;

    public ClientHandler() {
        Message packet = new Message();
        Header header = new Header();

        header.setType(MessageType.SYNC_GET.value());

        packet.setHeader(header);
        packet.setBody("md5");

        req = (JSONObject.toJSONString(packet)).getBytes();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println(body);
    }
}
