package com.newcitysoft.study.netty.filetransfer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.io.File;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/20 19:13
 */
public class FileClientInitializer extends ChannelInitializer<SocketChannel> {

    private File file;

    public FileClientInitializer(File file) {
        this.file = file;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加 ChunkedWriteHandler以处理作为ChunkedInput传入的数据
        pipeline.addLast(
                new StringDecoder(CharsetUtil.UTF_8),
                new StringEncoder(CharsetUtil.UTF_8),
                new ChunkedWriteHandler(),
                new FileClientHandler(file));

    }
}
