package com.newcitysoft.study.netty.technology.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Java NIO中的SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：
 * 打开一个SocketChannel并连接到互联网上的某台服务器。
 * 一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 15:43
 */
public class SocketChannelDemo {
    private SocketChannel channel;

    private void connect(String hostname, int port) throws IOException {
        channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(hostname, port));
    }

    private void read() throws IOException {

        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = channel.read(buf);

    }

    public void write() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        String data = "Hello World! The first of program what is write file use nio by me." + System.currentTimeMillis();

        byteBuffer.clear();
        byteBuffer.put(data.getBytes());
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
    }

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 5555));
        channel.configureBlocking(false);

        while (!channel.finishConnect()) {
            System.out.println("链接失败！");
        }


    }

}