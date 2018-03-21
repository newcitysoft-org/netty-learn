package com.newcitysoft.study.netty.filetransfer3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Netty初始化类.
 * 
 * @author 刘源
 */
public class Server {
	/**
	 * 日志组件.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	/**
	 * 服务端口.
	 */
	public static final int PORT = 7777;

	public Server() {
	}

	/**
	 * 
	 * 启动Netty的方法. 方法添加日期：2014-10-11 <br>
	 * 创建者:刘源
	 */
	public void initialize() {
		ServerBootstrap server = new ServerBootstrap();
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new InitializerPipeline()).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true);
			ChannelFuture f = server.bind(PORT).sync();
			LOGGER.debug("服务端口为:" + PORT);
			f.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			LOGGER.error("Netty启动异常：", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	@Test
	public void test() {
		new Server().initialize();
	}
}
