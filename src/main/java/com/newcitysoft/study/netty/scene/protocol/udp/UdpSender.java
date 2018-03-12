package com.newcitysoft.study.netty.scene.protocol.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 10:33
 */
public class UdpSender {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();

        String data = "第一个upd程序。。。。";
        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getLocalHost(), 9090);

        datagramSocket.send(packet);
        datagramSocket.close();

    }
}
