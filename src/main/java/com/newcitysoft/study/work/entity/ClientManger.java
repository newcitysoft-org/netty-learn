package com.newcitysoft.study.work.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/15 10:39
 */
public class ClientManger {
    /**
     * 组装消息头
     * @param type
     * @param attachment
     * @return
     */
    public static Header parseHeader(MessageType type, Map<String, Object> attachment) {
        Header header = new Header();

        header.setType(type.value());
        header.setAttachment(attachment);

        return header;
    }

    /**
     * 组装消息体
     * @param header
     * @param body
     * @return
     */
    public static Message parseMessage(Header header, Object body) {
        Message message = new Message();

        message.setHeader(header);
        message.setBody(body);

        return message;
    }

    public static String parseGetTaskMessage(MessageType type, String taskType) {
        Header header = parseHeader(type, null);
        Message taskMessage = new Message();

        taskMessage.setHeader(header);
        taskMessage.setBody(taskType);

        return JSONObject.toJSONString(taskMessage);
    }

    public static String parseReport(Object body, Map<String, Object> attachment) {
        Header header = parseHeader(MessageType.REPORT, attachment);
        Message message = parseMessage(header, body);

        return JSONObject.toJSONString(message);
    }
}
