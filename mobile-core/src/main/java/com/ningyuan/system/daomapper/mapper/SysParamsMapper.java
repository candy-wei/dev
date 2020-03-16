package com.ningyuan.system.daomapper.mapper;

import com.ningyuan.system.model.SysParamsModel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysParamsMapper extends Mapper<SysParamsModel>{

    List<SysParamsModel> getSysParams(Map<String, Object> params);

}

