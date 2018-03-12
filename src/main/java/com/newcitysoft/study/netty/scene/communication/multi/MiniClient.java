package com.newcitysoft.study.netty.scene.communication.multi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 
 * @Title: MiniClient
 * @Dscription: 客户端
 * @author Deleter
 * @date 2017年3月15日 下午12:52:58
 * @version 1.0
 */
public class MiniClient {

    public static final int STREAM_TEXT = 1;
    public static final int STREAM_FILE = 2;
    public static final int STREAM_IMG = 3;
    public static final int STREAM_VOICE = 4;
    public static final int STREAM_ASYNC = 5;

    private String hostName;
    private Integer port;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    private String title;
    private Hashtable<String, ArrayList<File>> asyncList;

    /**
     * 构造函数
     * 
     * @param hostName
     *            服务器地址
     * @param port
     *            端口
     */
    public MiniClient(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * 初始化
     * 
     * @throws UnknownHostException
     * @throws IOException
     */
    public void init() throws UnknownHostException, IOException {
        this.socket = new Socket(this.hostName, this.port);
        this.outputStream = new DataOutputStream(new BufferedOutputStream(
                this.socket.getOutputStream()));
        this.inputStream = new DataInputStream(new BufferedInputStream(
                this.socket.getInputStream()));
        this.asyncList = new Hashtable<String, ArrayList<File>>();
    }

    /**
     * 提交文件
     * 
     * @param dataType
     *            文件类型
     * @param file
     *            文件
     * @return 响应内容
     * @throws IOException
     */
    public String postFile(int dataType, File file) throws IOException {
        int len;
        byte[] buff = new byte[2048];
        // 读文件
        FileInputStream fis = new FileInputStream(file);
        // 写类型
        this.outputStream.writeInt(dataType);
        // 写总长度
        this.outputStream.writeLong(file.length());
        // 写文件名
        this.outputStream.writeUTF(file.getName());
        // 循环写
        while ((len = fis.read(buff)) != -1) {
            // 数据长度
            this.outputStream.writeInt(len);
            // 数据
            this.outputStream.write(buff, 0, len);
        }
        fis.close();// 关闭文件流
        this.outputStream.flush();
        return this.inputStream.readUTF();
    }

    /**
     * 提交文本
     * 
     * @param content
     *            文本内容
     * @return 响应内容
     * @throws IOException
     */
    public String postText(String content) throws IOException {
        // 写类型
        this.outputStream.writeInt(STREAM_TEXT);
        // 写长度
        this.outputStream.writeInt(content.length());
        // 写数据
        this.outputStream.writeUTF(content.toString());
        this.outputStream.flush();
        return this.inputStream.readUTF();
    }

    /**
     * 提交图文(设置标题)
     * 
     * @param title
     *            标题
     */
    public void setTitle(String title) {
        this.title = title;
        this.asyncList.put(title, new ArrayList<File>());
    }

    /**
     * 提交图文(添加文件)
     * 
     * @param file
     *            文件
     */
    public void addFile(File file) {
        if (this.asyncList.get(this.title) != null)
            this.asyncList.get(this.title).add(file);
    }

    /**
     * 提交图文
     * 
     * @return String 响应信息
     * @throws IOException
     */
    public String postAsync() throws IOException {
        int len;
        int size;
        byte[] buff = new byte[2048];
        FileInputStream fis;

        ArrayList<File> files = this.asyncList.get(this.title);

        // 写类型
        this.outputStream.writeInt(STREAM_ASYNC);
        // 写标题
        this.outputStream.writeUTF(this.title);
        size = asyncList.get(this.title).size();
        // 写文件数量
        this.outputStream.writeInt(size);

        for (int i = 0; i < size; i++) {
            // 读文件
            fis = new FileInputStream(files.get(i));
            // 写总长度
            this.outputStream.writeLong(files.get(i).length());
            // 写文件名
            this.outputStream.writeUTF(files.get(i).getName());
            // 循环写
            while ((len = fis.read(buff)) != -1) {
                // 数据长度
                this.outputStream.writeInt(len);
                // 数据
                this.outputStream.write(buff, 0, len);
            }
            fis.close();// 关闭文件流
            this.outputStream.flush();
        }
        // 清理内存
        this.asyncList.clear();
        return this.inputStream.readUTF();
    }

    /**
     * 关闭套接字
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        this.socket.close();
    }
}