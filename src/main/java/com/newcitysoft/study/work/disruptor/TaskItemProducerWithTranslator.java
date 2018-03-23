package com.newcitysoft.study.work.disruptor;

import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.newcitysoft.study.work.entity.TaskItem;

public class TaskItemProducerWithTranslator {
	private final RingBuffer<TaskItem> ringBuffer;
	public TaskItemProducerWithTranslator(RingBuffer<TaskItem> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	private static final EventTranslatorOneArg<TaskItem,String> TRANSLATOR = new EventTranslatorOneArg<TaskItem, String>()
	{
		@Override
		public void translateTo(TaskItem event, long sequence, String content)
		{
			event = JSONObject.parseObject(content, TaskItem.class);
		}
	};

	public void onData(String content)
	{
		ringBuffer.publishEvent(TRANSLATOR, content);
	}
}
