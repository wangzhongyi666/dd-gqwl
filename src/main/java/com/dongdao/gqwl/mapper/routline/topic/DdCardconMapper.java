package com.dongdao.gqwl.mapper.routline.topic;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;

public interface DdCardconMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long cardconid);

    int insert(DdCardcon record);

    int insertSelective(DdCardcon record);

    DdCardcon selectByPrimaryKey(Long cardconid);

    int updateByPrimaryKeySelective(DdCardcon record);

    int updateByPrimaryKey(DdCardcon record);
}