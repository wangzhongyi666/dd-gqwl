package com.dongdao.gqwl.mapper.websit;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.websit.GColumn;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GColumnMapper<T> extends BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(GColumn record);

    int insertSelective(GColumn record);

    GColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GColumn record);

    int updateByPrimaryKey(GColumn record);
}