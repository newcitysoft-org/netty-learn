package com.newcitysoft.study.work.netty.plugin;


import com.alibaba.fastjson.JSONArray;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.netty.client.Client;
import com.newcitysoft.study.work.util.MD5Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/14 13:47
 */
public class App2 {

    public static List<TaskResult> doTasks(List<TaskItem> items) {
        List<TaskResult> results = new LinkedList<>();
        items.forEach(task -> {
            TaskResult result = new TaskResult();

            result.setTaskId(task.getTaskId());
            result.setGetTime(task.getTimestamp());
            result.setReportTime(System.currentTimeMillis());
            result.setResult(MD5Utils.md5(task.getContent().toString()));

            System.out.println(task.getTaskId());
            results.add(result);
        });

        return results;
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = Client.getInstance();
        client.getTasks("md5", new TaskAsyncExecutor() {
            @Override
            public void execute(String tasks) {
                List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
                client.report(doTasks(tks), null);
            }

            @Override
            public void getResponse(String result) {
                System.out.println("sss");
            }
        });
    }
}
