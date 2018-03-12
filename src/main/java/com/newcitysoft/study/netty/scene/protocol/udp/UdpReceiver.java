package com.newcitysoft.study.netty.scene.protocol.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 11:14
 */
public class UdpReceiver {
    public static void main(String[] args) throws IOException {
        // 建立udp服务，并且监听一个端口
        DatagramSocket socket = new DatagramSocket(9090);
        // 准备空的数据包用于存放数据
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // 调用udp的服务接收
        socket.receive(packet);
        System.out.println("接收到的数据："+ new String(buf, 0, packet.getLength()));
        socket.close();
    }
}
