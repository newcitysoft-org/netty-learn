package com.newcitysoft.study.work.socket.client;

import com.alibaba.fastjson.JSONObject;
import com.newcitysoft.study.work.entity.TaskResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/12 16:21
 */
@Deprecated
public class TaskResultCache {
    private volatile static Map<String, List<TaskResult>> resultMap = new HashMap<>();
    private static Object lock = new Object();

    public static void put(String type, TaskResult result){
        synchronized (lock) {
            if(get(type) == null){
                List<TaskResult> list = new ArrayList<>();
                list.add(result);
                resultMap.put(type, list);
            }else {
                get(type).add(result);
            }
        }
    }

    public static List<TaskResult> get(String type) {
        System.out.println(JSONObject.toJSONString(resultMap.get(type)));
        return resultMap.get(type);
    }

    public static void clear(String type) {
        resultMap.remove(type);
    }
}
