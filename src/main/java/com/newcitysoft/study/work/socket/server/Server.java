package com.newcitysoft.study.work.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:38
 */
public class Server {
    public static void listen(int port) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                System.out.println("获取连接:"+socket);
                new Thread(new ServerHandler(socket)).start();
            }
        } finally {
            if(server != null) {
                server.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        listen(9090);
    }
}
