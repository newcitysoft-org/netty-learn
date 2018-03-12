package com.newcitysoft.study.netty.technology.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 通道之间的数据传输
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 14:04
 */
public class ChannelTransferDemo {

    public static void transferFrom(FileChannel from, FileChannel to, long position, long count) throws IOException {
        to.transferFrom(from, position, count);
    }

    public static void transferTo(FileChannel from, FileChannel to, long position, long count) throws IOException {
        from.transferTo(position, count, to);
    }

    /**
     * 文件拷贝
     * @param srcLocation
     * @param toLocation
     * @throws IOException
     */
    public static void copyFile(String srcLocation, String toLocation) throws IOException{
        RandomAccessFile fromFile = new RandomAccessFile(srcLocation, "rw");
        RandomAccessFile toFile = new RandomAccessFile(toLocation, "rw");

        FileChannel fromChannel = fromFile.getChannel();
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        transferFrom(fromChannel, toChannel, position, count);
        transferTo(fromChannel, toChannel, position, count);
    }

    public static void main(String[] args) throws IOException {
        copyFile("d:\\data\\from.txt", "d:\\data\\to.txt");
    }
}
