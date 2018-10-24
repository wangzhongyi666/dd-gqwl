package com.dongdao.gqwl.mapper.routline.topic;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.topic.DdTopic;

public interface DdTopicMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long topid);

    int insert(DdTopic record);

    int insertSelective(DdTopic record);

    DdTopic selectByPrimaryKey(Long topid);

    int updateByPrimaryKeySelective(DdTopic record);

    int updateByPrimaryKey(DdTopic record);
}