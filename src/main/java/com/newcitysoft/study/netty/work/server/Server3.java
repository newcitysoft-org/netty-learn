package com.newcitysoft.study.netty.work.server;

import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.scene.socket.bio.TimeServerHandler;
import com.newcitysoft.study.netty.scene.socket.fakeasync.TimeServerHandlerExecutePool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 19:20
 */
public class Server3 {
    public void connect(int port) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 1000);
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new ServerHandler(socket));
            }
        } finally {
            if(server != null) {
                System.out.println("The time server close");
                server.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = Const.BIO_PORT;
        new Server3().connect(port);
    }

}
