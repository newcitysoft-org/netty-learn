package com.newcitysoft.study.socket.communication.multi;

import com.newcitysoft.study.socket.communication.multi.handler.AsyncHandler;
import com.newcitysoft.study.socket.communication.multi.handler.FileHandler;
import com.newcitysoft.study.socket.communication.multi.handler.TextHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @Title: MiniServer
 * @Dscription: 服务器
 * @author Deleter
 * @date 2017年3月15日 下午12:53:07
 * @version 1.0
 */
public class MiniServer {
    private Integer port;
    private Socket socket;
    private ServerSocket serverSocket;

    private ThreadMiniServer threadMiniServer;

    public MiniServer(Integer port) {
        this.port = port;
    }

    public void init(String dirPath) throws IOException {
        serverSocket = new ServerSocket(this.port);
        while (true) {
            socket = serverSocket.accept();
            threadMiniServer = new ThreadMiniServer(socket, dirPath);
            threadMiniServer.setTextHandler(new TextHandler());
            threadMiniServer.setFileHandler(new FileHandler());
            threadMiniServer.setAsyncHandler(new AsyncHandler());
            threadMiniServer.start();
        }
    }
}