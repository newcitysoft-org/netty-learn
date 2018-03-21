package com.newcitysoft.study.netty.filetransfer.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/20 19:13
 */
public class FileServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(
                new StringDecoder(CharsetUtil.UTF_8),
                new StringEncoder(CharsetUtil.UTF_8),
                new ChunkedWriteHandler(),
                new FileServerHandler());
    }
}
