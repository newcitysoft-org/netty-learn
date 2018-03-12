package com.newcitysoft.study.netty.work.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.netty.work.entity.DataPacket;
import com.newcitysoft.study.netty.work.entity.PacketType;
import com.newcitysoft.study.netty.work.entity.SendItem;
import com.newcitysoft.study.netty.work.entity.Task;
import com.newcitysoft.study.netty.work.entity.TaskResult;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 14:47
 */
public class ClientHandler {

    public static final String TASK_TYPE = "md5";
    private static DataPacket packet = new DataPacket();
    private static Task task = new Task("md5", 20);
    static {
        packet.setType(PacketType.SYNCGET);
        packet.setBody(task);
    }

    public static String getTask() {
        return JSONObject.toJSONString(packet);
    }

    public static void handle(String resp) {
        try {

            System.out.println(resp);
            DataPacket<String> packet = JSONObject.parseObject(resp, DataPacket.class);

            List<SendItem> list = JSONArray.parseArray(JSONObject.toJSONString(packet.getBody()), SendItem.class);
            System.out.println(list.size());
            for (SendItem item : list) {
                executor.execute(new TaskExecutor(item));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String report(List<TaskResult> results) {
        DataPacket packet = new DataPacket();

        packet.setType(PacketType.REPORT);
        packet.setBody(results);

        return JSONObject.toJSONString(packet);
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 任务执行器
     */
    private static class TaskExecutor implements Runnable {
        private SendItem item;

        public TaskExecutor(SendItem item) {
            this.item = item;
        }

        @Override
        public void run() {
            TaskResult result = new TaskResult();

            result.setTaskId(item.getTaskId());
            result.setGetTime(item.getTimestamp());
            result.setReportTime(System.currentTimeMillis());
            result.setResult(MD5Utils.md5(item.getContent()));

            TaskResultCache.put(TASK_TYPE, result);
        }
    }

}
