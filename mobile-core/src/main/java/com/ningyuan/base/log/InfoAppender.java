package com.ningyuan.base.log;


import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 9:37 on 2018/7/11.
 * @Modified by:
 */
public class InfoAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        // 只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);
    }

}
