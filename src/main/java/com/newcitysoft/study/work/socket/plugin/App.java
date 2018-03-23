package com.newcitysoft.study.work.socket.plugin;

import com.alibaba.fastjson.JSONArray;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.socket.client.Client;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.util.MD5Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:13
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        Client client = Client.getInstance();

        client.asyncGetTasks("md5", new TaskAsyncExecutor<String>() {
            List<TaskResult> results = new LinkedList<>();
            @Override
            public void execute(String tasks) {
                List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);

                tks.forEach(task -> {
                    System.out.println(task.getTaskId());
                    TaskResult result = new TaskResult();

                    result.setTaskId(task.getTaskId());
                    result.setGetTime(task.getTimestamp());
                    result.setReportTime(System.currentTimeMillis());
                    result.setResult(MD5Utils.md5(task.getContent().toString()));

                    results.add(result);
                });

                //client.report(results);
            }

            @Override
            public void finish() {

            }

            @Override
            public List<TaskResult> report() {
                return results;
            }
        });

        System.out.println("aaa");

//        while (true) {
//            Thread.sleep(5000);
//            String tasks = client.getTasks("md5");
//
//            List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
//            List<TaskResult> results = new LinkedList<>();
//
//            tks.forEach(task -> {
//                System.out.println(task.getTaskId());
//                TaskResult result = new TaskResult();
//
//                result.setTaskId(task.getTaskId());
//                result.setGetTime(task.getTimestamp());
//                result.setReportTime(System.currentTimeMillis());
//                result.setResult(MD5Utils.md5(task.getContent().toString()));
//
//                results.add(result);
//            });
//
//            client.report(results);
//        }

    }
}
