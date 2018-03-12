package com.newcitysoft.study.netty.scene.socket.fakeasync;

import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.scene.socket.bio.TimeServerHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步IO
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
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 1000);
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } finally {
            if(server != null) {
                System.out.println("The time server close");
                server.close();
            }
        }
    }
}
