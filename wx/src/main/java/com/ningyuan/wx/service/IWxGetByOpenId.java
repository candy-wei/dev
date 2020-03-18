package com.ningyuan.wx.service;

import com.ningyuan.core.Context;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;

public interface IWxGetByOpenId<T> extends Mapper<T> {

    default Class<T> getRelateClass(){
        Class relateServiceClass = this.getClass().getInterfaces()[0];
        ParameterizedType parameterizedType = (ParameterizedType) relateServiceClass.getGenericInterfaces()[1];
        return  (Class) parameterizedType.getActualTypeArguments()[0];
    }

    default T getByOpenId(String openId){
        if(StringUtils.isEmpty(openId)){
            throw new RuntimeException("openId is null");
        }

        String key = this.getClass().getName() + openId;
        if (!Context.containTreadLocal(key)) {
            try {
                Example relateExample = new Example(getRelateClass());
                Example.Criteria criteria = relateExample.createCriteria();
                criteria.andEqualTo("openId", openId);
                Context.addTreadLocal(key, this.selectOneByExample(relateExample));
                return Context.getTreadLocal(key);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return Context.getTreadLocal(key);
    }
}
