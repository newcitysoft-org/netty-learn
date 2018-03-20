package com.newcitysoft.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 14:27
 */
public class SelectorDemo {

    public static void study() throws IOException {
        Socket socket = new Socket("127.0.0.1", 5555);
        // 1. Selector的创建
        Selector selector = Selector.open();
        SocketChannel channel = socket.getChannel();
        System.out.println(channel);
        // 2. 向Selector注册通道，并获取SelectionKey
        // 用“位或”操作符将常量连接起来
        channel.configureBlocking(false);
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        // 3.interest集合
        int interestSet = selectionKey.interestOps();
        // 4.ready集合
        int readySet = selectionKey.readyOps();
        selectionKey.isReadable();
        selectionKey.isAcceptable();
        selectionKey.isConnectable();
        selectionKey.isWritable();
        // 5.Channel + Selector
        selectionKey.channel();
        selectionKey.selector();
        // 6.附加的对象
        String obj = "Hello World!";
        selectionKey.attach(obj);
        String result = (String) selectionKey.attachment();

        SelectionKey key = channel.register(selector, SelectionKey.OP_READ, obj);
        // 7.获取已就绪的通道
        selector.select(2000);
        selector.select();
        selector.selectNow();
        // 8.已选择的key selectedKeys()
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
//        while (keyIterator.hasNext()) {
//            SelectionKey k = keyIterator.next();
//            if(k.isConnectable()) {
//
//            }else if(k.isAcceptable()) {
//
//            }else if(k.isReadable()) {
//
//            }else if(k.isWritable()) {
//
//            }
//            keyIterator.remove();
//        }
        // 9.wakeUp()
        selector.wakeup();
        // 10.close()
        selector.close();
    }

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 5555));
        Selector selector = Selector.open();

        socketChannel.configureBlocking(false);
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);

        while(true){
            int readyChannels = selector.select();
            if(readyChannels == 0){
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey k = keyIterator.next();
                if(k.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (k.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (k.isReadable()) {
                    // a channel is ready for reading
                } else if (k.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }
}
