package com.ningyuan.base.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class DataSourceSwitchAspect {

    @Before("execution(* com.ningyuan.*.daomapper.mapper.*.*(..))")
    public void mapper() {
        DataSourceSwitcher.setDataSourceKey(DataSourceType.SOURCE_MAPPER);
    }

    @Before("execution(* com.ningyuan.*.daomapper.dao.*.*(..))")
    public void dao(){
        DataSourceSwitcher.setDataSourceKey(DataSourceType.SOURCE_DAO);
    }

    @Before("execution(* com.ningyuan.*.daomapper.orm.*.*(..))")
    public void orm(){
        DataSourceSwitcher.setDataSourceKey(DataSourceType.SOURCE);
    }
}
