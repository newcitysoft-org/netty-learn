package com.newcitysoft.study.work.disruptor;

import com.lmax.disruptor.EventHandler;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;
import com.newcitysoft.study.work.entity.TaskItem;
import com.newcitysoft.study.work.netty.plugin.App2;

/**
 * 任务处理器
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-23 15:58
 */
public class TaskHandler implements EventHandler<TaskItem> {

	private TaskAsyncExecutor<TaskItem> executor;

	public TaskHandler(TaskAsyncExecutor<TaskItem> executor) {
		this.executor = executor;
	}

	@Override
	public void onEvent(TaskItem item, long sequence, boolean endOfBatch) throws Exception {
		this.executor.execute(item);
	}

}