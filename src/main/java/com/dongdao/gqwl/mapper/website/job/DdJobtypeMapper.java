package com.dongdao.gqwl.mapper.website.job;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.job.DdJobtype;

public interface DdJobtypeMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long jobtypeid);

    int insert(DdJobtype record);

    int insertSelective(DdJobtype record);

    DdJobtype selectByPrimaryKey(Long jobtypeid);

    int updateByPrimaryKeySelective(DdJobtype record);

    int updateByPrimaryKey(DdJobtype record);
}