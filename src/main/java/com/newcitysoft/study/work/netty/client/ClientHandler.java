package com.newcitysoft.study.work.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.common.Const;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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
    private TaskAsyncExecutor executor;

    private Channel channel;

    public ClientHandler(String content, TaskAsyncExecutor executor) {
        this.executor = executor;
        if(content != null && content.length() > 0) {
            this.req = (content + Const.delimiter).getBytes();
        }
    }

    public void sendGetTask() {
        if(req.length > 0){
            ByteBuf message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            channel.writeAndFlush(message);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("added");
        this.channel = ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        if(req.length > 0){
            ByteBuf message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
//            ChannelFuture cf = ctx.writeAndFlush(message);
//            cf.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                    System.out.println("完成操作！");
//                    executor.getResponse("aa");
//                }
//            });
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String resp = (String) msg;
        System.out.println(resp);
        try {
            Message message = JSONObject.parseObject(resp, Message.class);

            int type = message.getHeader().getType();
            Object body = message.getBody();

            String temp = null;
            if(body instanceof String) {
                temp = (String) body;
            }else {
                temp = JSONObject.toJSONString(body);
            }
            System.out.println(temp);
            MessageType messageType = MessageType.fromTypeName(type);
            System.out.println(messageType);
            switch (messageType) {
                case SEND:
                    executor.execute(temp);
                    break;
//                case RESPONSE:
//                    executor.getResponse(temp);
//                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }
}
