package com.newcitysoft.study.netty.work.client;


import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.work.entity.TaskResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:52
 */
public class Plugin {

    public static Socket connect(String host, int port) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static void writeAndFlush(PrintWriter out, String content) {
        out.println(content);
        out.flush();
    }

    public static String read(BufferedReader in) throws IOException {
        return in.readLine();
    }

    public static void close(BufferedReader in, PrintWriter out, Socket socket) {
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

    public static void communicate(Socket socket) {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while (true) {
                Thread.sleep(3000);
                try {
                    writeAndFlush(out, ClientHandler.getTask());
                    ClientHandler.handle(read(in));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            close(in, out, socket);
        }
    }

    public static void report(Socket socket) {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            List<TaskResult> taskResults = TaskResultCache.get(ClientHandler.TASK_TYPE);
            if(taskResults != null && taskResults.size() > 0) {
                String packet = ClientHandler.report(taskResults);
                writeAndFlush(out, packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(in, out, socket);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int port = Const.BIO_PORT;

        communicate(connect("127.0.0.1", port));
    }
}
