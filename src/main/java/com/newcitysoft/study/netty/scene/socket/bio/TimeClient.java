package com.newcitysoft.study.netty.scene.socket.bio;


import com.newcitysoft.study.netty.scene.socket.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:52
 */
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
        int port = Const.BIO_PORT;
        if(args!=null && args.length>0) {
            try {
                port = Integer.parseInt(args[0]);
            }catch (Exception e) {

            }
        }

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            while (true) {
                Thread.sleep(2000);
                out.println("QUERY TIME ORDER");
                out.flush();
                System.out.println("Send order 2 server succeed.");
                String resp = in.readLine();
                System.out.println("Now is :" +resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out.close();
            }

            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
