package com.newcitysoft.study.net.udp;

import java.io.IOException;
import java.net.*;

public class Sender {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        String data = "我的第一个udp程序！";
        DatagramPacket packet = new DatagramPacket(data.getBytes(),
                data.getBytes().length, InetAddress.getByName("127.0.0.1"), 9999);

        socket.send(packet);
        // 关闭资源
        socket.close();
    }
}
