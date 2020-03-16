package com.ningyuan.base;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public class BaseServiceImpl<M extends Mapper<T>, T> implements IBaseService<T> {

    protected transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected M mapper;


    private static class LIMIT_ONE {
        private static final RowBounds INSTANCE = new RowBounds(0, 1);
    }

    private static class LIMIT_TWO {
        private static final RowBounds INSTANCE = new RowBounds(0, 2);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return mapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t) {
        List<T> list = mapper.selectByRowBounds(t, LIMIT_TWO.INSTANCE);
        if (list.size() > 1) {
            throw new RuntimeException(" Expected one result (or null) to be returned by selectOne(), but found: more than 1");
        }
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int deleteByExample(Object o) {
        return mapper.deleteByExample(o);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return mapper.selectByExample(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return mapper.selectCountByExample(o);
    }

    @Override
    public T selectOneByExample(Object o) {
        List<T> list = mapper.selectByExampleAndRowBounds(o, LIMIT_TWO.INSTANCE);
        if (list.size() > 1) {
            throw new RuntimeException(" Expected one result (or null) to be returned by selectOneByExample(), but found: more than 1");
        }
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int updateByExample(T t, Object o) {
        return mapper.updateByExample(t, o);
    }

    @Override
    public int updateByExampleSelective(T t, Object o) {
        return mapper.updateByExampleSelective(t, o);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(o, rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return mapper.selectByRowBounds(t, rowBounds);
    }

    @Override
    public T selectLimitOne(T t) {
        List<T> list = mapper.selectByRowBounds(t, LIMIT_ONE.INSTANCE);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public T selectLimitOneByExample(Object o) {
        List<T> list = mapper.selectByExampleAndRowBounds(o, LIMIT_ONE.INSTANCE);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean exist(T record) {
        return mapper.selectCount(record) > 0;
    }

}
