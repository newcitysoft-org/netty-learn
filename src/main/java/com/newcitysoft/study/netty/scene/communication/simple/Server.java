package com.newcitysoft.study.netty.scene.communication.simple;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/7 14:53
 */
public class Server {
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket serverSocket = null;
        BufferedOutputStream bufferedOutput;
        BufferedInputStream bufferedInput;
        DataOutputStream outputStream;
        DataInputStream inputStream;
        try {
            serverSocket = new ServerSocket(1234);
            socket = serverSocket.accept();
            bufferedOutput = new BufferedOutputStream(socket.getOutputStream());
            bufferedInput = new BufferedInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(bufferedOutput);
            inputStream = new DataInputStream(bufferedInput);
            int dataType;
            while (true) {
                // 读类型
                dataType = inputStream.readInt();
                switch (dataType) {
                    case Const.STREAM_TEXT:
                        System.out
                                .println(receiveContent(inputStream, outputStream));
                        break;

                    case Const.STREAM_FILE:
                        receiveFile(Const.PATH_SERVER, Const.STREAM_FILE, inputStream,
                                outputStream);
                        break;
                    case Const.STREAM_IMG:
                        receiveFile(Const.PATH_SERVER, Const.STREAM_IMG, inputStream,
                                outputStream);
                        break;
                    case Const.STREAM_VOICE:
                        receiveFile(Const.PATH_SERVER, Const.STREAM_VOICE, inputStream,
                                outputStream);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            // 忽略
        }
    }

    /**
     * 文本信息传输协议
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static String receiveContent(DataInputStream inputStream,
                                        DataOutputStream outputStream) throws IOException {
        // 读长度
        int dataLength = inputStream.readInt();
        if (dataLength == 0) {
            return "txt_err";
        }
        String content = inputStream.readUTF();
        outputStream.writeUTF("txt_ok");
        outputStream.flush();
        return content;
    }

    /**
     * 文件传输协议
     * @param dirPath
     * @param dataType
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void receiveFile(String dirPath, int dataType,
                                   DataInputStream inputStream, DataOutputStream outputStream)
            throws IOException {
        String responseMsg = "";
        long totalLength = 0;
        int getLength = 0;
        byte[] buff = new byte[4096];
        switch (dataType) {
            case Const.STREAM_FILE:
                responseMsg = "file_ok";
                break;
            case Const.STREAM_IMG:
                responseMsg = "img_ok";
                break;
            case Const.STREAM_VOICE:
                responseMsg = "voice_ok";
                break;
            default:
                break;
        }
        // 读总长度
        long dataLength = inputStream.readLong();
        // 读文件名
        String fileName = inputStream.readUTF();
        // 读文件
        FileOutputStream fos = new FileOutputStream(new File(dirPath, fileName));
        while (true) {
            if (totalLength == dataLength) {
                break;
            }
            getLength = inputStream.readInt();
            totalLength += getLength;
            inputStream.read(buff, 0, getLength);
            fos.write(buff, 0, getLength);
        }
        fos.close();// 关闭文件流
        outputStream.writeUTF(responseMsg);
        outputStream.flush();
    }
}
