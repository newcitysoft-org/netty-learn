package com.newcitysoft.study.socket.communication.multi.handler;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 
 * @Title: AsyncHandler
 * @Dscription: 混合处理器
 * @author Deleter
 * @date 2017年3月15日 下午2:17:17
 * @version 1.0
 */
public class AsyncHandler implements BaseHandler<Hashtable<String, Object>> {

    @Override
    public void onMessage(Hashtable<String, Object> table) throws IOException {
        // 处理数据
    }
}