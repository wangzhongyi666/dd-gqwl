package com.dongdao.gqwl.mapper.website.job;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.job.DdJob;

import java.util.List;
import java.util.Map;

public interface DdJobMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long jobid);

    int insert(DdJob record);

    int insertSelective(DdJob record);

    DdJob selectByPrimaryKey(Long jobid);

    int updateByPrimaryKeySelective(DdJob record);

    int updateByPrimaryKey(DdJob record);

    List<Map<String,Object>> queryArea();
}