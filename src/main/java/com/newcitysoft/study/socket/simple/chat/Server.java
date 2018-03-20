package com.newcitysoft.study.socket.simple.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/5 14:48
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server  ready for chatting");

        Socket socket = serverSocket.accept();
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

        // opening channel to read from socket
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String receiveMessage;

        while(true)
        {
            //receive from client
            if((receiveMessage = receiveRead.readLine()) != null)
            {
                System.out.println(receiveMessage);
            }
            // sending to client
            pw.println(scanner.nextLine());
            pw.flush();
        }

    }
}
