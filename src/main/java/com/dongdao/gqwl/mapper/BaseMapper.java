package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.model.BaseModel;
import com.dongdao.gqwl.model.source.DdLabel;
import com.dongdao.gqwl.model.source.DdStype;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    void add(T t);

    void update(T t);

    void updateBySelective(T t);

    void delete(Object id);

    int queryByCount(BaseModel model);

    List<T> queryByList(BaseModel model);

    T queryById(Object id);

    int deleteByPrimaryKey(Object id);

    int insertSelective(T record);

    int updateByPrimaryKeySelective(T record);

    List<T> queryType();
    List<T> queryLabel();
    T selectByPrimaryKey(Object id);
    public List<Map> selectByType(Object type);
}
