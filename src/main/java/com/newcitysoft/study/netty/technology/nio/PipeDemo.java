package com.newcitysoft.study.netty.technology.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Java NIO 管道是2个线程之间的单向数据连接。
 * Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 16:54
 */
public class PipeDemo {

    public static void main(String[] args) throws IOException {
        // 1.创建管道
        Pipe pipe = Pipe.open();

        new WriteThread(pipe).start();
        new ReadThread(pipe).start();
    }

    public static class ReadThread extends Thread {
        Pipe pipe;
        public ReadThread(Pipe pipe) {
            this.pipe = pipe;
        }

        @Override
        public void run() {
            try {
                Pipe.SourceChannel sourceChannel = pipe.source();
                ByteBuffer buffer = ByteBuffer.allocate(48);
                int bytesRead = sourceChannel.read(buffer);
                System.out.println(bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class WriteThread extends Thread {
        Pipe pipe;
        public WriteThread(Pipe pipe) {
            this.pipe = pipe;
        }

        @Override
        public void run() {
            // 2.向管道写数据
            try {
                Pipe.SinkChannel sinkChannel = pipe.sink();
                String newData = "New String to write to file..." + System.currentTimeMillis();
                ByteBuffer buf = ByteBuffer.allocate(48);
                buf.clear();
                buf.put(newData.getBytes());

                buf.flip();

                while(buf.hasRemaining()) {
                    System.out.println(buf);
                    sinkChannel.write(buf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
