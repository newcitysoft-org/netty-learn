package com.newcitysoft.study.netty.filetransfer3.server;

import com.newcitysoft.study.netty.filetransfer3.entity.EchoFile;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;

public class ServerHandler extends ChannelHandlerAdapter {
	private static final Logger LOGGER= LoggerFactory.getLogger(ServerHandler.class);
	private String file_dir = "D:";
	private int dataLength=1024;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof EchoFile) {
			EchoFile ef = (EchoFile) msg;
			int SumCountPackage=ef.getSumCountPackage();
			int countPackage=ef.getCountPackage();
			byte[] bytes = ef.getBytes();
			//文件名
			String md5 = ef.getFile_md5();

			String path = file_dir + File.separator + md5;
			File file = new File(path);
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			randomAccessFile.seek(countPackage*dataLength-dataLength);
			randomAccessFile.write(bytes);
			LOGGER.debug("总包数："+ef.getSumCountPackage());
			LOGGER.debug("收到第"+countPackage+"包");
			LOGGER.debug("本包字节数:"+bytes.length);
			countPackage=countPackage+1;
			
			if (countPackage<=SumCountPackage) {
				ef.setCountPackage(countPackage);
				ctx.writeAndFlush(ef);
				randomAccessFile.close();
			} else {
				randomAccessFile.close();
				ctx.close();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
