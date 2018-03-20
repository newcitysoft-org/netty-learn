package com.newcitysoft.study.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ChatReceive extends Thread {

    @Override
    public void run() {
        try {
            // 建立udp的服务，要监听一个端口
            DatagramSocket socket = new DatagramSocket(6789);
            // 准备空的数据包存储数据
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            boolean flag = true;
            while(flag) {
                socket.receive(packet);
                System.out.println(packet.getAddress()+"："+new String(buf, 0, packet.getLength()));
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
