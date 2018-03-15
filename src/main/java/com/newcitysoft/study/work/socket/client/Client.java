package com.newcitysoft.study.work.socket.client;


import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.common.ClientManger;
import com.newcitysoft.study.work.entity.Header;
import com.newcitysoft.study.work.entity.Message;
import com.newcitysoft.study.work.entity.MessageType;
import com.newcitysoft.study.work.common.TaskAsyncExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:52
 */
public final class Client {
    private Socket socket = null;
    private InputStream is = null;
    private PrintWriter out = null;

    private final static String host = "127.0.0.1";
    private final static int port = 9090;

    private static Client instance = new Client();
    private Client(){}
    public static Client getInstance(){ return instance; }

    /**
     * 连接网络
     */
    private void connect() {
        try {
            socket = new Socket(host, port);
            is = socket.getInputStream();
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            connect();
        }
    }

    /**
     * 检测网络状况
     */
    private void checkNet() {
        if(socket == null || socket.isClosed()) {
            connect();
        }
    }

    /**
     * 同步获取任务
     * @param taskType
     * @return
     */
    public String getTasks(String taskType) {
        checkNet();
        try {
            if(is != null && out != null) {
                out.println(ClientManger.parseGetTaskMessage(MessageType.SYNC_GET, taskType));
                out.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String resp = reader.readLine();
                Message message = JSONObject.parseObject(resp, Message.class);

                Object body = message.getBody();
                if(body != null) {
                    if(body instanceof String) {
                        return (String) body;
                    } else {
                        return JSONObject.toJSONString(body);
                    }
                }
            }
        } catch (IOException e) {
            getTasks(taskType);
        } finally {
            try {
                close();
            } catch (IOException e1) {
                getTasks(taskType);
            }
        }

        return null;
    }

    /**
     * 上报任务结果
     * @param body
     */
    public void report(Object body) {
        checkNet();
        if(is != null && out != null) {
            Header header = ClientManger.parseHeader(MessageType.REPORT, null);
            Message message = ClientManger.parseMessage(header, body);

            try {
                out.println(JSONObject.toJSONString(message));
            }  finally {
                try {
                    close();
                } catch (IOException e1) {
                    report(body);
                }
            }
        }
    }

    /**
     * 异步获取任务
     * @param taskType
     * @param executor
     */
    public void asyncGetTasks(String taskType, TaskAsyncExecutor executor) {
        if(executor == null) {
            throw new IllegalArgumentException("TaskAsyncExecutor is null!");
        }
        try {
            checkNet();
            if(is != null && out != null) {
                out.println(ClientManger.parseGetTaskMessage(MessageType.ASYNC_GET, taskType));
                out.flush();

                boolean isGet = false;
                while(!isGet) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String resp = null;
                    resp = reader.readLine();

                    Message message = JSONObject.parseObject(resp, Message.class);
                    Object body = message.getBody();
                    String tasks = null;
                    if(body != null) {
                        if(body instanceof String) {
                            tasks = (String) body;
                        } else {
                            tasks = JSONObject.toJSONString(body);
                        }
                    }

                    executor.execute(tasks);

                    isGet = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     * @throws IOException
     */
    private void close() throws IOException {
        if(out != null) {
            out.close();
            out = null;
        }

        if(is != null) {
            is.close();
            is = null;
        }

        if(socket != null) {
            socket.close();
            socket = null;
        }
    }

    //    private Object parseMessageBody(Object body, Class<?> clazz) {
//        if(clazz == null) {
//            clazz = String.class;
//        }
//
//        if(body instanceof String) {
//            JSONObject.parseObject((String) body, clazz);
//        } else if(body instanceof JSONArray) {
//            return JSONObject.toJSONString(body);
//        } else if(body instanceof JSONObject) {
//
//        }
//
//        return null;
//    }

    public static void main(String[] args) {
        Client client = getInstance();
        String tasks = (String) client.getTasks("md5");
        System.out.println(tasks);
    }
}
