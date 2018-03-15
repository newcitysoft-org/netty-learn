package com.newcitysoft.study.work.socket.server;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.entity.Header;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import com.newcitysoft.study.work.entity.Result;
import com.newcitysoft.study.work.entity.ServerManager;
import com.newcitysoft.study.work.entity.TaskItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Socket数据包处理器
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:42
 */
public final class ServerHandler extends Thread{
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            String body = null;

            while (true) {
                body = in.readLine();
                System.out.println(body);
                if(body == null) {
                    break;
                }
                out.println(ServerManager.handle(ServerManager.decode(body)));
                out.flush();
            }
        } catch (IOException e) {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if(out != null) {
                out.close();
            }

            if(this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }

    }

    public static void main(String[] args) {
        Message dataPacket = new Message();
        Header header = new Header();

        header.setType(MessageType.SYNC_GET.value());

        dataPacket.setHeader(header);
        dataPacket.setBody("md5");

        System.out.println(JSONObject.toJSONString(dataPacket));
        System.out.println(JSONObject.toJSONString(ServerManager.handle(dataPacket)));
    }

}
