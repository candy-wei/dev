package com.ningyuan.system.dto;

import com.ningyuan.base.annotation.ExampleQuery;
import com.ningyuan.base.dto.BaseQueryDto;
import com.ningyuan.system.model.SysParamsModel;

public class SysParamsQueryDto extends BaseQueryDto<SysParamsModel> {
    @ExampleQuery(method = "andLike", valueClass = String.class)
    private String paramType;

    @ExampleQuery(method = "andLike", valueClass = String.class)
    private String paramKey;

    @ExampleQuery(method = "andLike", valueClass = String.class)
    private String paramValue;

    public String getParamType() { return paramType; }

    public void setParamType(String paramType) { this.paramType = paramType; }

    public String getParamKey() { return paramKey; }

    public void setParamKey(String paramKey) { this.paramKey = paramKey; }

    public String getParamValue() { return paramValue; }

    public void setParamValue(String paramValue) { this.paramValue = paramValue; }
}
