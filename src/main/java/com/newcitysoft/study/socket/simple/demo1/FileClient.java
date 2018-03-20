package com.newcitysoft.study.socket.simple.demo1;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/5 14:22
 */
public class FileClient {
    private Socket socket;

    public FileClient(String host, int port, String file) {
        try {
            socket = new Socket(host, port);
            sendFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String file) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[4096];
        while (fis.read(buffer) > 0){
            dos.write(buffer);
        }

        fis.close();
        dos.close();
    }

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        System.out.println("Enter host ip location!");
        String host = scanner.next();
        System.out.println("Enter number of port!");
        int port = scanner.nextInt();
        System.out.println("Enter file location with file name!");

        FileClient fc = new FileClient(host, port, scanner.next());
    }
}

