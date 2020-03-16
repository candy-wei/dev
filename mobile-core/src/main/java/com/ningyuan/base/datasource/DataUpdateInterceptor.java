package com.ningyuan.base.datasource;

import com.github.pagehelper.PageInterceptor;
import com.ningyuan.base.BaseModel;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Date;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class DataUpdateInterceptor extends PageInterceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        if(invocation.getArgs().length > 1 ){
            Object object = invocation.getArgs()[1];
            MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
            if(object instanceof BaseModel && SqlCommandType.UPDATE == mappedStatement.getSqlCommandType()){
                BaseModel model = (BaseModel) object;
                model.setUpdateTime(new Date());
            }
        }
         return invocation.proceed();
    }
}
