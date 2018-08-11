package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.model.source.DdPicsource;

public interface DdPicsourceMapper {
    int deleteByPrimaryKey(Long picid);

    int insert(DdPicsource record);

    int insertSelective(DdPicsource record);

    DdPicsource selectByPrimaryKey(Long picid);

    int updateByPrimaryKeySelective(DdPicsource record);

    int updateByPrimaryKey(DdPicsource record);
}