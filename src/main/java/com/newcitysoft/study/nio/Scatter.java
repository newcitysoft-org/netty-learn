package com.newcitysoft.study.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 14:00
 */
public class Scatter {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\data\\123.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = {header, body};

        fileChannel.write(bufferArray);
    }
}
