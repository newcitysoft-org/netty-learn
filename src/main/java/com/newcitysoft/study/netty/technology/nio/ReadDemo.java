package com.newcitysoft.study.netty.technology.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/5 19:26
 */
public class ReadDemo {

    private static final Charset charset = Charset.forName("GBK");
    public static void main(String[] args){
        try {
            RandomAccessFile aFile = new RandomAccessFile("D:\\data\\123.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {

                System.out.println("Read " + bytesRead);
                buf.flip();
                while(buf.hasRemaining()){
                    System.out.print(buf.get());
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
            aFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
