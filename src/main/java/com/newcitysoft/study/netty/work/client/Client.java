package com.newcitysoft.study.netty.work.client;


import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.netty.scene.socket.Const;
import com.newcitysoft.study.netty.work.entity.Header;
import com.newcitysoft.study.netty.work.entity.Message;
import com.newcitysoft.study.netty.work.entity.MessageType;
import com.newcitysoft.study.netty.work.entity.Task;
import com.newcitysoft.study.netty.work.entity.TaskResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/8 13:52
 */
public class Client {
    private Socket socket = null;
    private InputStream is = null;
    private OutputStream os = null;

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
            os = socket.getOutputStream();
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
     * 组装消息头
     * @param type
     * @param attachment
     * @return
     */
    private Header parseHeader(MessageType type, Map<String, Object> attachment) {
        Header header = new Header();

        header.setType(type.value());
        header.setAttachment(attachment);

        return header;
    }

    /**
     * 组装消息体
     * @param header
     * @param body
     * @return
     */
    private Message parseMessage(Header header, Object body) {
        Message message = new Message();

        message.setHeader(header);
        message.setBody(body);

        return message;
    }

    private String parseGetTaskMessage(String taskType) {
        Header header = parseHeader(MessageType.SYNC_GET, null);
        Message taskMessage = new Message();

        taskMessage.setHeader(header);
        taskMessage.setBody(taskType);

        return JSONObject.toJSONString(taskMessage);
    }


    /**
     * 获取任务
     */
    public String getTasks(String taskType) {
        checkNet();
        try {
            if(is != null && os != null) {
                os.write(parseGetTaskMessage(taskType).getBytes());

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String resp = reader.readLine();
                Message message = JSONObject.parseObject(resp, Message.class);

                Object body = message.getBody();

                if(body instanceof  String) {
                    return (String) body;
                } else {
                    return JSONObject.toJSONString(body);
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
     * 上报
     * @param body
     */
    public void report(Object body) {
        checkNet();
        if(is != null && os != null) {
            Header header = parseHeader(MessageType.REPORT, null);
            Message message = parseMessage(header, body);

            try {
                os.write(JSONObject.toJSONString(message).getBytes());
            } catch (IOException e) {
                report(body);
            } finally {
                try {
                    close();
                } catch (IOException e1) {
                    report(body);
                }
            }
        }
    }


    private void close() throws IOException {
        if(os != null) {
            os.close();
            os = null;
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


//    public void writeAndFlush(PrintWriter out, String content) {
//        out.println(content);
//        out.flush();
//    }


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

//    public static void communicate(Socket socket) {
//        BufferedReader in = null;
//        PrintWriter out = null;
//        try {
//            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream());
//
//            while (true) {
//                Thread.sleep(3000);
//                try {
//                    writeAndFlush(out, ClientHandler.getTask());
//                    ClientHandler.handle(read(in));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            close(in, out, socket);
//        }
//    }

//    public static void report(Socket socket) {
//        BufferedReader in = null;
//        PrintWriter out = null;
//        try {
//            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream());
//            while (true) {
//                Thread.sleep(3000);
//                List<TaskResult> taskResults = TaskResultCache.get(ClientHandler.TASK_TYPE);
//                if(taskResults != null && taskResults.size() > 0) {
//                    String packet = ClientHandler.report(taskResults);
//                    writeAndFlush(out, packet);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            close(in, out, socket);
//        }
//    }

    public static void main(String[] args) {
        Client client = getInstance();
        String tasks = client.getTasks("crawl");
        System.out.println(tasks);
    }
}
