package com.newcitysoft.study.work.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.newcitysoft.study.work.entity.TaskItem;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/23 10:59
 */
public class TaskProducer {
    private final RingBuffer<TaskItem> ringBuffer;
    public TaskProducer(RingBuffer<TaskItem> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(TaskItem item){
        //Grab the next sequence
        long sequence = ringBuffer.next();
        try{
            TaskItem event = ringBuffer.get(sequence);
            // Get the entry in the Disruptor
            // for the sequence（空的数据槽）
            event.setTimestamp(item.getTimestamp());
            event.setContent(item.getContent());
            event.setTaskId(item.getTaskId());
            //模拟从IO、文件等的输入流,在event中填充数据
        }finally{
            //不管成功与否，均将拿到的序列号归还
            ringBuffer.publish(sequence);
        }
    }
}
