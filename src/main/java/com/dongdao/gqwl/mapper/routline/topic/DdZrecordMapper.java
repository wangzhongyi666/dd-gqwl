package com.dongdao.gqwl.mapper.routline.topic;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.topic.DdZrecord;

public interface DdZrecordMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long zrecordid);

    int insert(DdZrecord record);

    int insertSelective(DdZrecord record);

    DdZrecord selectByPrimaryKey(Long zrecordid);

    int updateByPrimaryKeySelective(DdZrecord record);

    int updateByPrimaryKey(DdZrecord record);

    DdZrecord selectById(DdZrecord record);

    int selectByCard(DdZrecord zrecord);
}