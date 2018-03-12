package com.newcitysoft.study.netty.scene.socket.simple.demo1;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/5 14:33
 */
public class FileServer extends Thread{
    private ServerSocket ss;
    private String file;

    public FileServer(int port, String file) {
        try {
            ss = new ServerSocket(port);
            this.file = file;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock, String file) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        // Send file size in separate msg
        int filesize = 15123;
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
//        ss.close();
    }

    private void close(){
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter number of port!");
        int port = scanner.nextInt();
        System.out.println("Enter file location with file name!");
        FileServer fs = new FileServer(port, scanner.next());
        fs.start();
    }
}
