package com.newcitysoft.test.netty;

import com.alibaba.fastjson.JSONArray;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.socket.client.Client;
import com.newcitysoft.study.work.socket.plugin.MD5Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:13
 */
public class Client1 {
    public static void main(String[] args) {
        Client client = Client.getInstance();

        String tasks = client.getTasks("md5");

        List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
        List<TaskResult> results = new LinkedList<>();

        tks.forEach(task -> {
            TaskResult result = new TaskResult();

            result.setTaskId(task.getTaskId());
            result.setGetTime(task.getTimestamp());
            result.setReportTime(System.currentTimeMillis());
            result.setResult(MD5Utils.md5(task.getContent().toString()));

            results.add(result);
        });

        client.report(results);
    }
}
