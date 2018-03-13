package com.newcitysoft.study.netty.protocol;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/13 16:12
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;
    public MarshallingEncoder() throws IOException {
        //marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) throws Exception {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            //ByteOutput output = new ChannelBufferByteOutput(out);
            //marshaller.start(output);

        }finally {
            marshaller.close();
        }
    }
}
