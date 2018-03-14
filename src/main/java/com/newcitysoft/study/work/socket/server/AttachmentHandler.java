package com.newcitysoft.study.work.socket.server;

import java.util.Map;

/**
 * 附件处理器
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/14 13:37
 */
public interface AttachmentHandler {
    /**
     * 处理
     * @param attachment
     */
    void execute(Map<String, Object> attachment);
}
