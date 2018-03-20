package com.newcitysoft.study.socket.communication.multi;

import com.newcitysoft.study.socket.communication.multi.handler.AsyncHandler;
import com.newcitysoft.study.socket.communication.multi.handler.FileHandler;
import com.newcitysoft.study.socket.communication.multi.handler.TextHandler;
import com.newcitysoft.study.socket.communication.simple.Const;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @Title: ThreadMiniServer
 * @Dscription: 服务线程
 * @author Deleter
 * @date 2017年3月15日 下午2:37:20
 * @version 1.0
 */
public class ThreadMiniServer extends Thread {

    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private String dirPath;
    private int dataType;
    private TextHandler textHandler;
    private FileHandler fileHandler;
    private AsyncHandler asyncHandler;

    public ThreadMiniServer(Socket socket, String dirPath) {
        this.socket = socket;
        this.dirPath = dirPath;
    }

    public TextHandler getTextHandler() {
        return textHandler;
    }

    public void setTextHandler(TextHandler textHandler) {
        this.textHandler = textHandler;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public AsyncHandler getAsyncHandler() {
        return asyncHandler;
    }

    public void setAsyncHandler(AsyncHandler asyncHandler) {
        this.asyncHandler = asyncHandler;
    }

    @Override
    public void run() {
        try {
            outputStream = new DataOutputStream(new BufferedOutputStream(
                    socket.getOutputStream()));
            inputStream = new DataInputStream(new BufferedInputStream(
                    socket.getInputStream()));
            while (true) {
                // 读类型
                dataType = inputStream.readInt();
                switch (dataType) {
                    case MiniClient.STREAM_TEXT:
                        textHandler.onMessage(receiveContent(this.inputStream,
                                this.outputStream));
                        break;

                    case MiniClient.STREAM_FILE:
                        fileHandler.onMessage(receiveFile(this.dirPath,
                                Const.STREAM_FILE, this.inputStream,
                                this.outputStream));
                        break;
                    case MiniClient.STREAM_IMG:
                        fileHandler.onMessage(receiveFile(this.dirPath,
                                Const.STREAM_IMG, this.inputStream,
                                this.outputStream));
                        break;
                    case MiniClient.STREAM_VOICE:
                        fileHandler.onMessage(receiveFile(this.dirPath,
                                Const.STREAM_VOICE, this.inputStream,
                                this.outputStream));
                        break;
                    case MiniClient.STREAM_ASYNC:
                        asyncHandler.onMessage(receiveAsync(this.dirPath,
                                this.inputStream, this.outputStream));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            // 忽略EOF
        }
    }

    /**
     * 文本信息传输协议
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    private String receiveContent(DataInputStream inputStream,
                                  DataOutputStream outputStream) throws IOException {
        // 读长度
        int dataLength = inputStream.readInt();
        if (dataLength == 0) {
            return "text_err";

        }
        String content = inputStream.readUTF();
        outputStream.writeUTF("text_ok");
        outputStream.flush();
        return content;
    }

    /*
     * 文件传输协议
     * @param dirPath 目录路径
     * @param dataType 数据类型
     */
    private String receiveFile(String dirPath, int dataType,
                               DataInputStream inputStream, DataOutputStream outputStream)
            throws IOException {
        String responseMsg = "";
        long totalLength = 0;
        int getLength = 0;
        byte[] buff = new byte[2048];
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
        File file = new File(dirPath, fileName);
        // 写文件
        FileOutputStream fos = new FileOutputStream(file);
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
        return file.getAbsolutePath();
    }

    /*
     * 混合传输协议
     * @param dirPath 目录路径
     */
    private Hashtable<String, Object> receiveAsync(String dirPath,
                                                   DataInputStream inputStream, DataOutputStream outputStream)
            throws IOException {

        int size;
        int index = 0;
        int getLength;
        long dataLength;
        long totalLength;
        String title;
        String fileName;
        File file;
        FileOutputStream fos;

        byte[] buff = new byte[2048];
        StringBuilder sb = new StringBuilder();
        Hashtable<String, Object> table = new Hashtable<>();
        ArrayList<String> filePaths = new ArrayList<>();

        title = inputStream.readUTF();// 读标题
        table.put("title", title);
        size = inputStream.readInt();// 读取图片数量
        table.put("size", size);

        while (index++ < size) {

            getLength = 0;
            dataLength = 0;
            totalLength = 0;

            dataLength = inputStream.readLong();// 读总长
            fileName = inputStream.readUTF();// 读文件名
            // 写文件
            file = new File(dirPath, System.currentTimeMillis() + fileName);
            fos = new FileOutputStream(file);
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
            sb.append(index);
            sb.append(":ok,");
            filePaths.add(file.getAbsolutePath());// 添加文件路径
        }
        table.put("paths", filePaths);
        outputStream.writeUTF(sb.substring(0, sb.length() - 1));
        outputStream.flush();
        return table;
    }
}