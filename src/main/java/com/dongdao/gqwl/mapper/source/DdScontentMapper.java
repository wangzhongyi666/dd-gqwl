package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.source.DdScontent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DdScontentMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long s_contentid);

    int insert(DdScontent record);

    int insertSelective(DdScontent record);

    DdScontent selectByPrimaryKey(Long s_contentid);

    int updateByPrimaryKeySelective(DdScontent record);

    int updateByPrimaryKeyWithBLOBs(DdScontent record);

    int updateByPrimaryKey(DdScontent record);
}