package com.newcitysoft.study.work.disruptor;

import com.lmax.disruptor.EventHandler;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.netty.plugin.App2;

public class TaskHandler implements EventHandler<TaskItem> {
	@Override
	public void onEvent(TaskItem item, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("item>>>" + item.toString());
		System.out.println("result>>>" + App2.doTasks(item));
	}

}