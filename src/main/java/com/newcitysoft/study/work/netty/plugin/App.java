package com.newcitysoft.study.work.netty.plugin;


import com.alibaba.fastjson.JSONArray;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.netty.client.Client;
import com.newcitysoft.study.work.util.MD5Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/14 13:47
 */
public class App {

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Client client = null;

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            client.getTasks("md5", new TaskAsyncExecutor() {
                @Override
                public void execute(String tasks) {
                    List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
                    List<TaskResult> results = new LinkedList<>();
                    tks.forEach(task -> {
                        TaskResult result = new TaskResult();

                        result.setTaskId(task.getTaskId());
                        result.setGetTime(task.getTimestamp());
                        result.setReportTime(System.currentTimeMillis());
                        result.setResult(MD5Utils.md5(task.getContent().toString()));

                        System.out.println(task.getTaskId());
                        results.add(result);
                    });

                    client.report(results, null);
                }

                @Override
                public void getResponse(String result) {
                    System.out.println("sss");
                }
            });
        }
    };

    public static void main(String[] args) throws InterruptedException {
        client = Client.getInstance();
        while (true) {
            Thread.sleep(5000);
            executorService.execute(runnable);
        }
    }
}
