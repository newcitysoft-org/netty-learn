package com.newcitysoft.study.netty.http;

import com.newcitysoft.study.netty.http.ssl.SecureSocketSslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Http服务端通信通道初始化类
 * @author lixin.tian@renren-inc.com
 * @date 2018-03-19 15:54
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    private final boolean isSslSupport;
    private final boolean isClient;

    public HttpServerInitializer(boolean isSslSupport, boolean isClient) {
        this.isSslSupport = isSslSupport;
        this.isClient = isClient;
    }


    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if(isSslSupport) {
            SSLEngine engine =
                    SecureSocketSslContextFactory.getServerContext().createSSLEngine();
            engine.setUseClientMode(false);
            p.addFirst(new SslHandler(engine));
        }

        if(isClient) {
            p.addLast(new HttpClientCodec());
        } else {
            p.addLast(new HttpServerCodec());
        }

        p.addLast(new HttpObjectAggregator(1024*1024));
        p.addLast(new HttpServerHandler());
    }
}
