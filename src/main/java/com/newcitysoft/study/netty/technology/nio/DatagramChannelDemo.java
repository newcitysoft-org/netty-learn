package com.newcitysoft.study.netty.technology.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Java NIO中的DatagramChannel是一个能收发UDP包的通道。
 * 因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 16:47
 */
public class DatagramChannelDemo {
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        channel.receive(buf);
        //发送数据
        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf2 = ByteBuffer.allocate(48);
        buf2.clear();
        buf2.put(newData.getBytes());
        buf2.flip();

        int bytesSent = channel.send(buf2, new InetSocketAddress("jenkov.com", 80));
    }
}
