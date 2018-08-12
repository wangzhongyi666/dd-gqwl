package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.source.DdPicsource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DdPicsourceMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long picid);

    int insert(DdPicsource record);

    int insertSelective(DdPicsource record);

    DdPicsource selectByPrimaryKey(Long picid);

    int updateByPrimaryKeySelective(DdPicsource record);

    int updateByPrimaryKey(DdPicsource record);
}