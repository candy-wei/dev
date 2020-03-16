package com.ningyuan.base.dto;

import com.ningyuan.base.annotation.ExampleQuery;
import com.ningyuan.base.constant.SortEnum;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class BaseQueryDto<T> {

    protected String field;

    protected SortEnum order;

    public Example toExample() {
        Class <T> tClass = (Class <T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                ExampleQuery exampleQuery = field.getAnnotation(ExampleQuery.class);
                if (exampleQuery != null) {
                    if (exampleQuery.hasValue()) {
                        Example.Criteria.class.getMethod(exampleQuery.method(), String.class, exampleQuery.valueClass()).invoke(criteria, field.getName(), field.get(this));
                    } else {
                        if (field.getBoolean(this)) {
                            Example.Criteria.class.getMethod(exampleQuery.method(), String.class).invoke(criteria, field.getName());
                        }
                    }
                }
            }

            if (!StringUtils.isEmpty(field)) {
                if (order != null && SortEnum.desc.equals(order)) {
                    example.orderBy(field).desc();
                } else {
                    example.orderBy(field).asc();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("查询条件转换失败");
        }
        return example;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortEnum getOrder() {
        return order;
    }

    public void setOrder(SortEnum order) {
        this.order = order;
    }
}
