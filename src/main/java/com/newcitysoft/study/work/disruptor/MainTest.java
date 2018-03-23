package com.newcitysoft.study.work.disruptor;

import com.alibaba.fastjson.JSONArray;
import com.lmax.disruptor.RingBuffer;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.entity.TaskResult;
import com.newcitysoft.study.work.netty.client.Client;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/23 11:11
 */
public class MainTest {
    private static Executor executor = Executors.newCachedThreadPool();
    private static TaskItemFactory factory = new TaskItemFactory();
    private static TaskItemHandler handler = new TaskItemHandler();
    @Test
    public void test() {

        DisruptorFactory factory = new DisruptorFactory<TaskItem>(executor, MainTest.factory, handler);
        factory.start();
        RingBuffer<TaskItem> ringBuffer = factory.getRingBuffer();

        TaskItemProducer producer = new TaskItemProducer(ringBuffer);

        Client client = Client.getInstance();
        client.getTasks("md5", new TaskAsyncExecutor() {
            List<TaskResult> taskResults = new ArrayList<>();

            @Override
            public void execute(String tasks) {
                List<TaskItem> tks = JSONArray.parseArray(tasks, TaskItem.class);
                tks.forEach(task -> {
                    producer.onData(task);
                });
            }

            @Override
            public void finish() {
                System.out.println("Report is success!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public List<TaskResult> report() {
                return taskResults;
            }
        });
    }
}
