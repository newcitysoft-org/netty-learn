package com.newcitysoft.test.netty;

import src.file.FileClient;

/**
 * Created by Administrator on 2015/8/25.
 */
public class TestClient {

    public static void main(String[] args) {
        FileClient client = new FileClient();
        client.start();
        client.sendFile("D:\\data\\test\\456.txt", "127.0.0.1:8000");
    }

}
