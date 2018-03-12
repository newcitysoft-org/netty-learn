package com.newcitysoft.study.netty.scene.socket.simple.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/5 14:52
 */
public class Client {
    public static void main(String[] args) throws IOException {
        // creating Socket on server port and first parameter represent ip address of server
        Socket sock = new Socket("127.0.0.1", 5000);
        Scanner scanner=new Scanner(System.in);

        //opening channel to write sending message on socket
        PrintWriter pwrite = new PrintWriter(sock.getOutputStream(), true);
        // opening channel to read from socket
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        System.out.println("Start the chitchat, type and press Enter key");
        String receiveMessage;

        while(true) {
            // sending to server
            pwrite.println(scanner.nextLine());
            pwrite.flush(); // flush the data
            //receive from server
            if((receiveMessage = receiveRead.readLine()) != null) {
                // displaying at DOS prompt
                System.out.println(receiveMessage);
            }
        }
    }
}
