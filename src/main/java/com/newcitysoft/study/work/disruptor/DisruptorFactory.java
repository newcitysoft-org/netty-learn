package com.newcitysoft.study.work.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executor;

/**
 * Disruptor工厂类
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/23 11:40
 */
public class DisruptorFactory<T> {
    private Disruptor<T> disruptor = null;
    private Executor executor;
    private EventFactory factory;
    private EventHandler handler;

    public DisruptorFactory(Executor executor, EventFactory factory, EventHandler handler) {
        this.executor = executor;
        this.factory = factory;
        this.handler = handler;
    }

    private void config() {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;
        disruptor = new Disruptor<T>(
                this.factory,
                bufferSize,
                this.executor,
                ProducerType.MULTI,
                new YieldingWaitStrategy());

        disruptor.handleEventsWith(this.handler);
    }

    public void start() {
        if(disruptor != null) {
            disruptor.start();
        }else {
            config();
            start();
        }
    }

    public RingBuffer<T> getRingBuffer() {
        RingBuffer<T> ringBuffer = disruptor.getRingBuffer();
        return ringBuffer;
    }
}
