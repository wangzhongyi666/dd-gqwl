package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.GColumn;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GColumnMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GColumn record);

    int insertSelective(GColumn record);

    GColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GColumn record);

    int updateByPrimaryKey(GColumn record);
}