package com.ningyuan.base.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceSwitcher extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataSourceType> dataSourceKey = new ThreadLocal<DataSourceType>();

    @Override
    protected Object determineCurrentLookupKey() {
        if(dataSourceKey.get() != null){
            return dataSourceKey.get().getType();
        }
        return null;
    }

    public static void setDataSourceKey(DataSourceType dataSource) {
        dataSourceKey.set(dataSource);
    }

    public static DataSourceType getType() {
        return dataSourceKey.get();
    }

}
