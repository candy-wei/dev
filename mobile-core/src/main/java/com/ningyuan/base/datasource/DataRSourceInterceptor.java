package com.ningyuan.base.datasource;


import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
public class DataRSourceInterceptor extends PageInterceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        switch (DataSourceSwitcher.getType()) {
            case SOURCE_MAPPER:
                DataSourceSwitcher.setDataSourceKey(DataSourceType.SOURCE_MAPPER_R);
                return super.intercept(invocation);
            case SOURCE_DAO:
                DataSourceSwitcher.setDataSourceKey(DataSourceType.SOURCE_DAO_R);
                return super.intercept(invocation);
            default:
                return super.intercept(invocation);
        }
    }
}
