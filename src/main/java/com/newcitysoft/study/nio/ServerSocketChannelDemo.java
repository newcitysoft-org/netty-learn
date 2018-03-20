package com.newcitysoft.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 15:54
 */
public class ServerSocketChannelDemo {
    private volatile static int size = 0;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(5555));
        serverSocketChannel.configureBlocking(false);

        while(true){
            SocketChannel socketChannel =
                    serverSocketChannel.accept();
            if(socketChannel != null) {
                System.out.println("服务端接受请求！size="+(++size));
            }
        }
    }
}
