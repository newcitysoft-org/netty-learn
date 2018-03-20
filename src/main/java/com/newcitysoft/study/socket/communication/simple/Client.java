package com.newcitysoft.study.socket.communication.simple;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/7 14:05
 */
public class Client {

    public static void main(String[] args) {

        Socket socket;
        BufferedOutputStream bufferedOutput;
        BufferedInputStream bufferedInput;
        DataOutputStream outputStream;
        DataInputStream inputStream;
        String responseMsg; // 响应内容
        // 测试内容
        StringBuilder content = new StringBuilder();
        try {
            // 连接地址localhost,端口1234
            socket = new Socket("localhost", 1234);
            // 包装流
            bufferedOutput = new BufferedOutputStream(socket.getOutputStream());
            bufferedInput = new BufferedInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(bufferedOutput);
            inputStream = new DataInputStream(bufferedInput);
            /*
             * 发送文本信息
             */
            for (int i = 0; i < 100; i++) {
                // 追加文本x100
                content.append("测试文本");
            }

            Thread.sleep(5000);
            responseMsg = writeContent(content.toString(), inputStream,
                    outputStream);
            System.out.println(responseMsg);
            /*
             * 发送文件
             */
            responseMsg = writeFile(new File("D:\\data\\socket\\client\\db.rar"), Const.STREAM_FILE,
                    inputStream, outputStream);
            System.out.println(responseMsg);
            Thread.sleep(5000);
            System.out.println("发送压缩包完成！");
            /*
             * 发送图片
             */
            System.out.println("发送图片！");
            responseMsg = writeFile(new File("D:\\data\\socket\\client\\db.jpg"), Const.STREAM_IMG,
                    inputStream, outputStream);
            System.out.println(responseMsg);
            Thread.sleep(5000);
            /*
             * 发送音频
             */
            responseMsg = writeFile(new File("D:\\data\\socket\\client\\db.avi"), Const.STREAM_VOICE,
                    inputStream, outputStream);
            System.out.println(responseMsg);
            socket.close();// 关闭套接字
        } catch (IOException e) {
            e.printStackTrace();// IO异常
        } catch (InterruptedException e) {
            e.printStackTrace();// 线程中断异常
        }
    }

    /**
     * 文本信息传输协议
     * @param content
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static String writeContent(String content,
                                      DataInputStream inputStream, DataOutputStream outputStream)
            throws IOException {

        // 写类型
        outputStream.writeInt(Const.STREAM_TEXT);
        // 写长度
        outputStream.writeInt(content.length());
        // 写数据
        outputStream.writeUTF(content.toString());
        outputStream.flush();
        return inputStream.readUTF();
    }

    /**
     * 文件传输协议
     * @param file
     * @param dataType
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static String writeFile(File file, int dataType,
                                   DataInputStream inputStream, DataOutputStream outputStream)
            throws IOException {
        int len;
        byte[] buff = new byte[4096];
        // 读文件
        FileInputStream fis = new FileInputStream(file);
        // 写类型
        outputStream.writeInt(dataType);
        // 写总长度
        outputStream.writeLong(file.length());
        // 写文件名
        outputStream.writeUTF(file.getName());
        // 循环写
        while ((len = fis.read(buff)) != -1) {
            // 数据长度
            outputStream.writeInt(len);
            // 数据
            outputStream.write(buff, 0, len);
        }
        fis.close();// 关闭文件流
        outputStream.flush();
        String responseMsg = inputStream.readUTF();
        return responseMsg;
    }
}


