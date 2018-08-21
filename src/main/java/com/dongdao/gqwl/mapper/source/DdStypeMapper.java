package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.source.DdStype;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DdStypeMapper <T> extends BaseMapper {
    int deleteByPrimaryKey(Long s_typeid);

    int insert(DdStype record);

    int insertSelective(DdStype record);

    DdStype selectByPrimaryKey(Long s_typeid);

    int updateByPrimaryKeySelective(DdStype record);

    int updateByPrimaryKey(DdStype record);
}