package com.newcitysoft.study.netty.scene.socket.bio;

import com.newcitysoft.study.netty.scene.socket.Const;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:38
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = Const.BIO_PORT;
        if(args!=null && args.length>0) {
            try {
                port = Integer.parseInt(args[0]);
            }catch (Exception e) {

            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            while (true) {
                System.out.println("开始等待...");
                socket = server.accept();
                System.out.println("获取连接:"+socket);
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if(server != null) {
                System.out.println("The time server close");
                server.close();
            }
        }
    }
}
