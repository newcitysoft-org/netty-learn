package com.newcitysoft.study.work.netty.plugin;


import com.alibaba.fastjson.JSONArray;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.netty.client.Client;
import com.newcitysoft.study.work.util.MD5Utils;

import java.util.ArrayList;
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
            results.add(doTasks(task));
        });

        return results;
    }

    public static TaskResult doTasks(TaskItem task) {
        TaskResult result = new TaskResult();

        result.setTaskId(task.getTaskId());
        result.setGetTime(task.getTimestamp());
        result.setReportTime(System.currentTimeMillis());
        result.setResult(MD5Utils.md5(task.getContent().toString()));

        return result;
    }

    public static void main(String[] args) {
        Client client = Client.getInstance();
        client.getTasks("md5", new TaskAsyncExecutor<String>() {
            List<TaskResult> taskResults = new ArrayList<>();

            @Override
            public void execute(String tasks) {
                List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
                taskResults.addAll(doTasks(tks));
            }

            @Override
            public void finish() {
                System.out.println("Report is success!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main(args);
            }

            @Override
            public List<TaskResult> report() {
                return taskResults;
            }
        });
    }
}
