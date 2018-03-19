package com.newcitysoft.test.netty;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.util.HttpClientUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试类
 * @author yunfeng.cheng
 * @create 2016-07-25
 */
public class HttpTest {

    public static void main(String[] args) throws Exception{
//		sendGet();
		sendPostJson();
//        sendPostForm();
//        sendPost();
    }

    private static void sendPostJson() throws Exception{
        String path = "http://127.0.0.1:8080";
        JSONObject obj = new JSONObject();
        obj.put("id", "10001");
        obj.put("name", "yunfengCheng");
        obj.put("sex", "M");
        String jsonStr = obj.toJSONString();
        byte[] data = jsonStr.getBytes();
        java.net.URL url = new java.net.URL(path);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode() == 200){
            BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) conn.getInputStream(), "UTF-8"));
            String msg = in.readLine();
            System.out.println("msg: " + msg);
            in.close();
        }
        conn.disconnect();
    }

    private static void sendPostForm() throws Exception{
        String path = "http://127.0.0.1:8080/";
        String parm = "id=10001&name=yunfengCheng&sex=M";
        byte[] data = parm.getBytes();
        java.net.URL url = new java.net.URL(path);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) conn.getInputStream(), "UTF-8"));
            String msg = in.readLine();
            System.out.println("msg: " + msg);
            in.close();
        }
        conn.disconnect();
    }

    public static void sendPost() {
        try {
            String url="http://127.0.0.1:8080/";

            Map<String, String> map = new HashMap<String, String>();
            map.put("code", "js");
            map.put("day", "0");
            map.put("city", "上海");
            map.put("dfc", "1");
            map.put("charset", "utf-8");
            String body = null;
            body = HttpClientUtil.doPost(url, map,"utf-8");
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendGet() throws Exception{
        String path = "http://127.0.0.1:8080/";
        String reqUrl = path + "?id=10001&name=yunfengCheng&sex=M";
        java.net.URL url = new java.net.URL(reqUrl);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.connect();
        if (conn.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String msg = in.readLine();
            System.out.println(msg);
            in.close();
        }
        conn.disconnect();
    }

}
