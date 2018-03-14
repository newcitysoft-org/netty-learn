package com.newcitysoft.study.work.socket.client;

/**
 * 任务异步执行器
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/14 15:39
 */
public interface TaskAsyncExecutor {
    /**
     * 执行回调方法
     * @param tasks
     */
    void execute(String tasks);
}
