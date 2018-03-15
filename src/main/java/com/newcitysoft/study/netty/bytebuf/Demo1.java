package com.newcitysoft.study.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/15 10:17
 */
public class Demo1 {
    private static final Charset charset = CharsetUtil.UTF_8;
    public static void slice() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", charset);
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(charset));
        sliced.setByte(0, (byte)'J');
        assert buf.getByte(0) == sliced.getByte(0);
        System.out.println(buf.toString(charset));
    }

    public static void copy() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", charset);
        ByteBuf sliced = buf.copy(0, 15);
        System.out.println(sliced.toString(charset));
        sliced.setByte(0, (byte)'J');
        assert buf.getByte(0) == sliced.getByte(0);
        System.out.println(buf.toString(charset));
    }

    public static void main(String[] args) {
        copy();
    }
}
