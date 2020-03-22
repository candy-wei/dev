package com.ningyuan.mobile.service;

import com.ningyuan.base.IBaseService;
import com.ningyuan.mobile.model.ShopAttrKey;
import com.ningyuan.mobile.model.ShopAttrVal;

import java.util.List;

public interface IAttrKeyService extends IBaseService<ShopAttrKey> {
    List<ShopAttrVal> getAttrVals();
}
