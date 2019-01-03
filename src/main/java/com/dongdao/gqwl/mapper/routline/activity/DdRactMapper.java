package com.dongdao.gqwl.mapper.routline.activity;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.activity.DdRact;

public interface DdRactMapper <T> extends BaseMapper {
    int deleteByPrimaryKey(Long r_actid);

    int insert(DdRact record);

    int insertSelective(DdRact record);

    DdRact selectByPrimaryKey(DdRact record);

    int updateByPrimaryKeySelective(DdRact record);

    int updateByPrimaryKey(DdRact record);
}