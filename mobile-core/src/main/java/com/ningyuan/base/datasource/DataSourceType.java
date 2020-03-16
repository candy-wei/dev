package com.ningyuan.base.datasource;

public enum DataSourceType {

    SOURCE_MAPPER("dataSourceMapper"),

    SOURCE_MAPPER_R("dataSourceMapperR"),

    SOURCE_DAO("dataSourceDao"),

    SOURCE_DAO_R("dataSourceDaoR"),

    SOURCE("dataSourceMapper");

    private String type;

    DataSourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
