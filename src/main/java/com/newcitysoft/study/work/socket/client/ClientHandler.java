package com.newcitysoft.study.work.socket.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.entity.Header;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import com.newcitysoft.study.work.entity.Task;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.socket.plugin.MD5Utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 14:47
 */
public class ClientHandler {

    public static final String TASK_TYPE = "md5";
    private static Message packet = new Message();
    private static Task task = new Task("md5", 20);
    static {
        Header header = new Header();

        header.setType(MessageType.SYNC_GET.value());

        packet.setHeader(header);
        packet.setBody(task);
    }

    public static String getTask() {
        return JSONObject.toJSONString(packet);
    }

    public static void handle(String resp) {
        try {

            System.out.println(resp);
            Message packet = JSONObject.parseObject(resp, Message.class);

            List<TaskItem> list = JSONArray.parseArray(JSONObject.toJSONString(packet.getBody()), TaskItem.class);
            System.out.println(list.size());
            for (TaskItem item : list) {
                executor.execute(new TaskExecutor(item));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String report(List<TaskResult> results) {
        Message packet = new Message();

        Header header = new Header();

        header.setType(MessageType.REPORT.value());

        packet.setHeader(header);
        packet.setBody(results);

        return JSONObject.toJSONString(packet);
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 任务执行器
     */
    private static class TaskExecutor implements Runnable {
        private TaskItem item;

        public TaskExecutor(TaskItem item) {
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
