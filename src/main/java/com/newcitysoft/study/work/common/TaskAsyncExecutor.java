package com.newcitysoft.study.work.common;

import com.newcitysoft.study.work.entity.TaskResult;

import java.util.List;

/**
 * 任务异步执行器
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/14 15:39
 */
public interface TaskAsyncExecutor {
    /**
     * 执行回调方法，但不支持直接上传
     * @param tasks
     */
    void execute(String tasks);
    /**
     * 处理服务器相应
     */
    void finish();
    /**
     * 上报
     * @return
     */
    List<TaskResult> report();
}
