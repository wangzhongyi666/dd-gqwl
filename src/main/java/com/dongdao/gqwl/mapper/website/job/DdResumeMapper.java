package com.dongdao.gqwl.mapper.website.job;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.job.DdResume;

public interface DdResumeMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long resumeid);

    int insert(DdResume record);

    int insertSelective(DdResume record);

    DdResume selectByPrimaryKey(Long resumeid);

    int updateByPrimaryKeySelective(DdResume record);

    int updateByPrimaryKey(DdResume record);
}