package com.newcitysoft.study.netty.technology.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。
 * FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 15:08
 */
public class FileChannelDemo {

    public static void read() throws IOException {
        RandomAccessFile file = new RandomAccessFile("d:\\data\\123.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);

        int length = channel.read(buffer);
        while(length != -1){
            buffer.flip();

            if(buffer.hasRemaining()){
                System.out.print((char) buffer.get());
            }

            buffer.clear();
            length = channel.read(buffer);
        }

        file.close();
    }

    public static void write() throws IOException {
        RandomAccessFile file = new RandomAccessFile("d:\\data\\456.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        String data = "Hello World! The first of program what is write file use nio by me."+ System.currentTimeMillis();

        byteBuffer.clear();
        byteBuffer.put(data.getBytes());
        byteBuffer.flip();
        System.out.println("通道目前的位置："+channel.position());
        System.out.println("通道目前的大小："+channel.size());
        while(byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
        System.out.println(channel.position());
        close(file, channel);
    }

    public static void close(RandomAccessFile file, FileChannel channel) throws IOException {
        channel.close();
        file.close();
    }

    public static void main(String[] args) throws IOException {
//        read();
        write();
    }
}
