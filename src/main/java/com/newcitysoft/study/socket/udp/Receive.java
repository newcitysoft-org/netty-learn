package com.newcitysoft.study.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receive {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(9999);
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        System.out.println("信息收到："+ new String(data, 0, packet.getLength()));
    }
}
