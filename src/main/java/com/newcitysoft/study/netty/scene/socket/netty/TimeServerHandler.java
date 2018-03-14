package com.newcitysoft.study.netty.scene.socket.netty;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:16
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        // 读取客户端的数据
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());
        String body = (String) msg;

        System.out.println("The time server receive order:"+body +"; the counter is :" + ++counter);
        // 返回数据
        String data = response(body);
        //String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        data = data + "$_";
        System.out.println(data);
        ByteBuf resp = Unpooled.copiedBuffer(data.getBytes());
        // ctx.write(resp);
        ctx.writeAndFlush(resp);
    }

    public String response(String request) {
        String result = "";
        if(request != null && request.length()>0) {
            if("QUERY TIME ORDER".equalsIgnoreCase(request)) {
                result = new Date(System.currentTimeMillis()).toString();
            }else {
                try {
                    Message packet = JSONObject.parseObject(request, Message.class);
                    MessageType messageType = MessageType.fromTypeName(packet.getHeader().getType());

                    switch (messageType) {
                        case SYNC_GET:
                            break;
                        case ASYNC_GET:
                            break;
                        case REPORT:
                            break;
                        default:
                            break;
                    }
                    result = packet.getHeader().getType() + "";
                } catch (Exception e) {
                    result = "Bad order!";
                }
            }
        }

        return result;
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
