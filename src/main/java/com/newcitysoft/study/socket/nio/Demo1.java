package com.newcitysoft.study.socket.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 16:35
 */
public class Demo1 {

    public static void main(String[] args) throws IOException {
        //1.打开ServerSocketChannel，用于监听客户端的连接，它是客户端连接的父管道。
        ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
        //2.绑定监听端口，设置连接为非阻塞模式
        acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 5000));
        acceptorSvr.configureBlocking(false);
        //3.创建Reactor线程，创建多路复用并启动线程
        Selector selector = Selector.open();
//        new Thread(new ReactorTask());
        //4.将ServerSocketChannel注册到Reactor线程的多路复用器Selector上，监听ACCEPT事件
        /*new Object == toHandler*/
        SelectionKey key = acceptorSvr.register(selector, SelectionKey.OP_CONNECT, new Object());
        //5.多路复用器在线程run方法的无限循环体内轮训准备就绪的Key
        int num = selector.select();
        Set selectedKeys = selector.selectedKeys();
        Iterator it = selectedKeys.iterator();
        while (it.hasNext()) {
            SelectionKey k = (SelectionKey) it.next();
        }
    }
}
