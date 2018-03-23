package com.newcitysoft.study.work.netty.plugin;

import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.netty.client.Client;

import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/23 16:00
 */
public class App3 {
    public static void main(String[] args) {
        Client client = Client.getInstance();

        client.getTasks("md5", new TaskAsyncExecutor<TaskItem>() {
            @Override
            public void execute(TaskItem task) {
                System.out.println(App2.doTasks(task));
            }

            @Override
            public void finish() {

            }

            @Override
            public List<TaskResult> report() {
                return null;
            }
        });
    }
}
