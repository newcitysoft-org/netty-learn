package com.newcitysoft.study.netty.code;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/19 10:52
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
