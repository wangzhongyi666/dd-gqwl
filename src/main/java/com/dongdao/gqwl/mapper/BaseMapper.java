package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.model.BaseModel;
import com.dongdao.gqwl.model.source.DdLabel;
import com.dongdao.gqwl.model.source.DdStype;

import java.util.List;

public interface BaseMapper<T> {

    public void add(T t);

    public void update(T t);

    public void updateBySelective(T t);

    public void delete(Object id);

    public int queryByCount(BaseModel model);

    public List<T> queryByList(BaseModel model);

    public T queryById(Object id);

    int deleteByPrimaryKey(Object id);

    int insertSelective(T record);

    int updateByPrimaryKeySelective(T record);

    public List<T> queryType();

}
