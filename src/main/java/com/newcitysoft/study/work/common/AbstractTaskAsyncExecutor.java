package com.newcitysoft.study.work.common;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/15 18:43
 */
public abstract class AbstractTaskAsyncExecutor implements TaskAsyncExecutor{
    @Override
    public final void execute(String tasks) {

    }

    public abstract Object exec(String tasks);

    @Override
    public void getResponse(String result) {

    }
}
