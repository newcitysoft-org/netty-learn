package com.newcitysoft.study.netty.scene.communication.multi.handler;

import java.io.IOException;

/**
 * 
 * @Title: BaseHandler
 * @Dscription: 基本处理器
 * @author Deleter
 * @date 2017年3月15日 下午2:06:52
 * @version 1.0
 */
public interface BaseHandler<T> {

    /**
     * 当数据到达
     * 
     * @param t
     *            数据
     * @throws IOException
     */
    void onMessage(T t) throws IOException;
}