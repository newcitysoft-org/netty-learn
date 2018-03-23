package com.newcitysoft.study.work.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/23 11:11
 */
public class MainTest {
    private static Executor executor = Executors.newCachedThreadPool();

    private static class Message {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Test
    public void test2() throws InterruptedException {

        EventFactory<Message> factory = new EventFactory<Message>() {
            @Override
            public Message newInstance() {
                return new Message();
            }
        };

        EventHandler<Message> handler = new EventHandler<Message>() {
            @Override
            public void onEvent(Message s, long l, boolean b) throws Exception {
                System.out.println(s.getContent());
            }
        };

        DisruptorFactory disruptorFactory = new DisruptorFactory<Message>(executor, factory, handler);
        disruptorFactory.start();
        RingBuffer<Message> ringBuffer = disruptorFactory.getRingBuffer();

       for (int i=0; i<100; i++) {
           //Grab the next sequence
           long sequence = ringBuffer.next();
           try{
               Message message = ringBuffer.get(sequence);

               message.setContent(System.currentTimeMillis() + "-" + i);
           }finally{
               ringBuffer.publish(sequence);
               System.out.println("生产："+i);
               Thread.sleep(10);
           }
       }
    }
}
