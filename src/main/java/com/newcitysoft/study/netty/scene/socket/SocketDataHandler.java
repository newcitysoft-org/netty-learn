package com.newcitysoft.study.netty.scene.socket;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.netty.scene.socket.entity.DataPacket;
import com.newcitysoft.study.netty.scene.socket.entity.PacketType;
import com.newcitysoft.study.netty.scene.socket.entity.Result;
import com.newcitysoft.study.netty.scene.socket.entity.SendItem;
import com.newcitysoft.study.netty.scene.socket.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Socket数据包处理器
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 10:36
 */
public class SocketDataHandler {

    /**
     * 处理数据包
     * @param packet
     * @return
     */
    public static DataPacket handle(DataPacket packet) {
        DataPacket<Result> resp = checkPacket(packet);
        if(resp.getBody().getResult() == Result.RESULT_FAILURE) {
            return resp;
        }else {
            DataPacket result = new DataPacket();
            switch (packet.getType()) {
                case SYNCGET:
                    result = handleSyncGet(packet);
                    break;
                case ASYNCGET:
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
    private static DataPacket handleSyncGet(DataPacket packet){
        DataPacket resp = new DataPacket();
        try{
            String body = JSONObject.toJSONString(packet.getBody());
            Task req = JSONObject.parseObject(body, Task.class);

            List<SendItem> list = new ArrayList<SendItem>();
            list.add(new SendItem(getTaskId(), "15841694657", System.currentTimeMillis()));
            list.add(new SendItem(getTaskId(), "18840114833", System.currentTimeMillis()));
            list.add(new SendItem(getTaskId(), "15040124451", System.currentTimeMillis()));
            list.add(new SendItem(getTaskId(), "15566543218", System.currentTimeMillis()));

            resp.setType(PacketType.SEND);
            resp.setBody(list);

        }catch (Exception e) {
            e.printStackTrace();
            resp.setType(PacketType.RESPONSE);
            resp.setBody(packagingErrorPacket(Result.Code.ERROR_ENCODE, "数据封装错误！"));
        }

        return resp;
    }

    /**
     * 处理异步获取
     * @param packet
     * @return
     */
    private static DataPacket handleAsyncGet(DataPacket packet){
        DataPacket resp = new DataPacket();
        return resp;
    }

    /**
     * 处理上报任务
     * @param packet
     * @return
     */
    private static DataPacket handleReport(DataPacket packet){
        DataPacket resp = new DataPacket();
        return resp;
    }

    /**
     * 处理相应信息
     * @param packet
     * @return
     */
    private static DataPacket handleResponse(DataPacket packet){
        DataPacket resp = new DataPacket();
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
    private static DataPacket<Result> checkPacket(DataPacket packet) {
        DataPacket<Result> resp = new DataPacket<>();
        Result result = new Result();

        if(packet == null || packet.getBody().toString().length() == 0) {
            result = packagingErrorPacket(Result.Code.ERROR_NULL_PACKET, "空数据包！");
        }else if(packet.getType() == null) {
            result = packagingErrorPacket(Result.Code.ERROR_NULL_TYPE, "空类型!");
        }else if(!PacketType.contains(packet.getType().getType().toUpperCase())) {
            result = packagingErrorPacket(Result.Code.ERROR_EVENT, "没有该事件存在！");
        }

        resp.setType(PacketType.RESPONSE);
        resp.setBody(result);

        return resp;
    }

    public static void main(String[] args) {
        DataPacket<Task> dataPacket = new DataPacket();
        Task task = new Task("md5", 20);

        dataPacket.setType(PacketType.SYNCGET);
        dataPacket.setBody(task);

        System.out.println(JSONObject.toJSONString(dataPacket));
        System.out.println(JSONObject.toJSONString(handle(dataPacket)));
    }
}
