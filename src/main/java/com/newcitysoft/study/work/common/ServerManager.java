package com.newcitysoft.study.work.common;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.entity.Header;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import com.newcitysoft.study.work.entity.Result;
import com.newcitysoft.study.work.entity.TaskItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/15 14:08
 */
public class ServerManager {
    /**
     * 解码
     * @param req
     * @return
     */
    public static Message decode(String req) {
        return JSONObject.parseObject(req, Message.class);
    }

    /**
     * 处理数据包
     * @param packet
     * @return
     */
    public static Message handle(Message packet) {
        Message resp = checkPacket(packet);
        if(((Result)resp.getBody()).getResult() == Result.RESULT_FAILURE) {

        }else {
            Message result = new Message();
            switch (MessageType.fromTypeName(packet.getHeader().getType())) {
                case SYNC_GET:
                    result = handleSyncGet(packet);
                    break;
                case ASYNC_GET:
                    result = handleAsyncGet(packet);
                    break;
                case REPORT:
                    result = handleReport(packet);
                    break;
                case RESPONSE:
                    result = handleResponse(packet);
                    break;
                default:
                    break;
            }
            resp =  result;
        }

        System.out.println("server-handler:" + resp);
        return resp;
    }

    private static final Random random = new Random();

    private static String getTaskId() {
        return Integer.toHexString(random.nextInt());
    }

    @Deprecated
    public static Object getTasks(String type) {
        if("md5".equals(type)) {
            List<TaskItem> list = new ArrayList<TaskItem>();
            list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));

            return list;
        }

        return null;
    }


    /**
     * 处理同步获取事件
     * @param packet
     * @return
     */
    private static Message handleSyncGet(Message packet){
        Message resp = new Message();
        Header header = new Header();
        try{
            String body = (String) packet.getBody();
            if(body != null) {
                header.setType(MessageType.SEND.value());

                resp.setHeader(header);
                resp.setBody(getTasks(body));
            }
        }catch (Exception e) {
            e.printStackTrace();
            header.setType(MessageType.RESPONSE.value());

            resp.setHeader(header);
            resp.setBody(packagingErrorPacket(Result.Code.ERROR_ENCODE, "数据封装错误！"));
        }

        return resp;
    }

    /**
     * 模拟异步发送任务情况
     * @return
     */
    @Deprecated
    private static Object getAsyncTask() {
        try {
            Thread.sleep(1000*60*2);
            return getTasks("md5");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理异步获取
     * @param packet
     * @return
     */
    private static Message handleAsyncGet(Message packet){
        Message resp = new Message();
        Header header = new Header();

        Object body = packet.getBody();

        if(body != null) {
            header.setType(MessageType.SYNC_GET.value());
            resp.setHeader(header);
            resp.setBody(getAsyncTask());
        }
        return resp;
    }

    /**
     * 处理上报任务
     * @param packet
     * @return
     */
    private static Message handleReport(Message packet){
        Message resp = new Message();
        Header header = new Header();
        Result result = new Result();

        header.setType(MessageType.RESPONSE.value());

        result.setCode(Result.Code.SUCCESS);

        resp.setHeader(header);
        resp.setBody(result);

        return resp;
    }

    /**
     * 处理相应信息
     * @param packet
     * @return
     */
    private static Message handleResponse(Message packet){
        Message resp = new Message();
        return resp;
    }

    /**
     * 封装错误包
     * @param errorCode
     * @param description
     * @return
     */
    private static Result packagingErrorPacket(int errorCode, String description) {
        Result result = new Result();

        result.setResult(Result.RESULT_FAILURE);
        result.setCode(errorCode);
        result.setDescription(description);

        return result;
    }

    /**
     * 判断是否为空包
     * @param packet
     * @return
     */
    private static Message checkPacket(Message packet) {
        Message resp = new Message();
        Result result = new Result();

        if(packet == null || packet.getBody().toString().length() == 0) {
            result = packagingErrorPacket(Result.Code.ERROR_NULL_PACKET, "空数据包！");
        }else if(!MessageType.contains(packet.getHeader().getType())) {
            result = packagingErrorPacket(Result.Code.ERROR_EVENT, "没有该事件存在！");
        }
//        else if(packet.getHeader().getType()) {
//            result = packagingErrorPacket(Result.Code.ERROR_NULL_TYPE, "空类型!");
//        }
        Header header = new Header();

        header.setType(MessageType.RESPONSE.value());
        resp.setHeader(header);
        resp.setBody(result);

        return resp;
    }
}
