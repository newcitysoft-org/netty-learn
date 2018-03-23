package com.newcitysoft.study.work.netty.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.RingBuffer;
import com.newcitysoft.study.work.common.ClientManger;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.disruptor.DisruptorFactory;
import com.newcitysoft.study.work.disruptor.TaskProducer;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/9 11:36
 */
public class ClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    private Message message;
    private DisruptorFactory factory;

    public ClientHandler(Message message, DisruptorFactory factory) {
        this.factory = factory;
        this.message = message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture future = ctx.writeAndFlush(message);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("操作成功！");
            }
        });
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println(message.toString());
        handleChannelRead(ctx, this.factory, message);
    }

    /**
     * 处理通道读操作
     * @param ctx
     * @param factory
     * @param message
     */
    private void handleChannelRead(ChannelHandlerContext ctx, DisruptorFactory factory, Message message) {
        int type = message.getHeader().getType();
        Object body = message.getBody();

        String temp = null;
        if(body instanceof String) {
            temp = (String) body;
        }else {
            temp = JSONObject.toJSONString(body);
        }

        MessageType messageType = MessageType.fromTypeName(type);
        switch (messageType) {
            case SEND:
                //execAndReport(ctx, executor, temp);
                produce(temp);
                break;
            case RESPONSE:
                //executor.finish();
                break;
            default:
                break;
        }
    }

    public void produce(String tasks) {
        if(factory != null){
            RingBuffer<TaskItem> ringBuffer = this.factory.getRingBuffer();
            TaskProducer producer = new TaskProducer(ringBuffer);
            List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
            tks.forEach(task -> {
                producer.onData(task);
            });
        }
    }

    /**
     * 执行并且上报任务
     * @param ctx
     * @param executor
     * @param temp
     */
    private static void execAndReport(ChannelHandlerContext ctx, TaskAsyncExecutor executor, String temp) {
        // 1.任务执行
        executor.execute(temp);
        // 2.获取任务执行结果
        List<TaskResult> results = executor.report();
        // 3.上报任务结果
        if(results != null && results.size() > 0) {
            Message report = ClientManger.parseReport(results, null);
            ChannelFuture future = ctx.writeAndFlush(report);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    // 4.任务上报完成回调
                    executor.finish();
                }
            });
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }
}
