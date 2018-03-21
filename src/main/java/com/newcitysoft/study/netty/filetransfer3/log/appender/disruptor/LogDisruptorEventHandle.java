package com.newcitysoft.study.netty.filetransfer3.log.appender.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者处理类.
 * 
 * @author 创建者:刘源
 */

public class LogDisruptorEventHandle implements EventHandler<LogValueEvent> {

	public LogDisruptorEventHandle() {

	}

	@Override
	public void onEvent(LogValueEvent event, long sequence, boolean endOfBatch) {
		event.getParent().appendLoopOnAppenders(event.getEventObject());
		
	}

}
