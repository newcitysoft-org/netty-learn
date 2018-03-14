package com.newcitysoft.study.netty.work.server;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.netty.work.entity.Header;
import com.newcitysoft.study.netty.work.entity.Message;
import com.newcitysoft.study.netty.work.entity.MessageType;
import com.newcitysoft.study.netty.work.entity.Result;
import com.newcitysoft.study.netty.work.entity.TaskItem;
import com.newcitysoft.study.netty.work.entity.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Socket数据包处理器
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:42
 */
public class ServerHandler extends Thread{
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            String body = null;

            while (true) {
                body = in.readLine();
                if(body == null) {
                    break;
                }
                out.println(JSONObject.toJSONString(handle(decode(body))));
                out.flush();
            }
        } catch (IOException e) {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if(out != null) {
                out.close();
            }

            if(this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }

    }

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
        System.out.println(MessageType.fromTypeName(packet.getHeader().getType()));
        Message resp = checkPacket(packet);
        if(((Result)resp.getBody()).getResult() == Result.RESULT_FAILURE) {
            return resp;
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
            return result;
        }
    }

    private static final Random random = new Random();

    private static String getTaskId() {
        return Integer.toHexString(random.nextInt());
    }

    /**
     * 处理同步获取事件，
     * @param packet
     * @return
     */
    private static Message handleSyncGet(Message packet){
        Message resp = new Message();
        Header header = new Header();
        try{
            String body = (String) packet.getBody();

            List<TaskItem> list = new ArrayList<TaskItem>();
            list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));

            header.setType(MessageType.SEND.value());

            resp.setHeader(header);
            resp.setBody(list);

        }catch (Exception e) {
            e.printStackTrace();
            header.setType(MessageType.RESPONSE.value());

            resp.setHeader(header);
            resp.setBody(packagingErrorPacket(Result.Code.ERROR_ENCODE, "数据封装错误！"));
        }

        return resp;
    }

    private static List<TaskItem> list = new ArrayList<TaskItem>();
    static {
        list.add(new TaskItem(getTaskId(), "15841694657", System.currentTimeMillis()));
        list.add(new TaskItem(getTaskId(), "18840114833", System.currentTimeMillis()));
        list.add(new TaskItem(getTaskId(), "15040124451", System.currentTimeMillis()));
        list.add(new TaskItem(getTaskId(), "15566543218", System.currentTimeMillis()));
    }
    /**
     * 处理异步获取
     * @param packet
     * @return
     */
    private static Message handleAsyncGet(Message packet){
        Message resp = new Message();
        return resp;
    }

    /**
     * 处理上报任务
     * @param packet
     * @return
     */
    private static Message handleReport(Message packet){
        Message resp = new Message();
        System.out.println(packet.getBody());
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

    public static void main(String[] args) {
        Message dataPacket = new Message();
        Task task = new Task("md5", 20);
        Header header = new Header();

        header.setType(MessageType.SYNC_GET.value());

        dataPacket.setHeader(header);
        dataPacket.setBody(task);

        System.out.println(JSONObject.toJSONString(dataPacket));
        System.out.println(JSONObject.toJSONString(handle(dataPacket)));
    }

}
