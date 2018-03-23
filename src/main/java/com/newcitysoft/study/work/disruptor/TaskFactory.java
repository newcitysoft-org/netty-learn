package com.newcitysoft.study.work.disruptor;

import com.lmax.disruptor.EventFactory;
import com.newcitysoft.study.work.entity.TaskItem;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/23 10:58
 */
public class TaskFactory implements EventFactory<TaskItem> {
    @Override
    public TaskItem newInstance() {
        return new TaskItem();
    }
}
