package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.source.DdLabel;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DdLabelMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long s_label_id);

    int insert(DdLabel record);

    int insertSelective(DdLabel record);

    DdLabel selectByPrimaryKey(Long s_label_id);

    int updateByPrimaryKeySelective(DdLabel record);

    int updateByPrimaryKey(DdLabel record);
}