package com.newcitysoft.study.net.udp;

public class Chat {
    public static void main(String[] args) {
        ChatReceive receive = new ChatReceive();
        receive.start();

        ChatSender sender = new ChatSender();
        sender.start();
    }
}
