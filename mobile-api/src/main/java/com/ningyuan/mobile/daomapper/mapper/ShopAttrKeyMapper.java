package com.ningyuan.mobile.daomapper.mapper;

import com.ningyuan.mobile.model.ShopAttrKey;
import com.ningyuan.mobile.model.ShopAttrVal;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ShopAttrKeyMapper extends Mapper<ShopAttrKey> {
    List<ShopAttrVal> getAttrVals(Long keyId);
}
