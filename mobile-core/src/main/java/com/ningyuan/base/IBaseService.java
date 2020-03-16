package com.ningyuan.base;

import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;

public interface IBaseService<T> extends Mapper<T> {

    default Class getTClass() {
        return (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[1];
    }

    T selectLimitOne(T t);

    T selectLimitOneByExample(Object o);

    boolean exist(T record);

}
