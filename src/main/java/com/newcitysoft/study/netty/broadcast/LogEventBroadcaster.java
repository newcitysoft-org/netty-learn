package com.newcitysoft.study.netty.broadcast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

/**
 * Listing 13.3 LogEventBroadcaster
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() {
        Channel ch = null;
        try {
            ch = bootstrap.bind(0).sync().channel();

            long pointer = 0;
            for (;;) {
                long len = file.length();
                if (len < pointer) {
                    // file was reset
                    pointer = len;
                } else if (len > pointer) {
                    // Content was added
                    RandomAccessFile raf = new RandomAccessFile(file, "r");
                    raf.seek(pointer);
                    String line;
                    while ((line = raf.readLine()) != null) {
                        System.out.println(line);
                        ch.writeAndFlush(new LogEvent(null, -1,
                                file.getAbsolutePath(), encode(line)));
                    }
                    pointer = raf.getFilePointer();
                    raf.close();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encode(String str) {
        try {
            return new String(str.getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255",
                        LogEventConst.PORT), new File(LogEventConst.FILE_NAME));
        try {
            broadcaster.run();
        }
        finally {
            broadcaster.stop();
        }
    }
}
