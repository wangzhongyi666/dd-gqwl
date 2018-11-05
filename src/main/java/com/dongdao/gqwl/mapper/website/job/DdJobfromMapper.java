package com.dongdao.gqwl.mapper.website.job;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.job.DdJobfrom;

import java.util.List;
import java.util.Map;

public interface DdJobfromMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long jobfromid);

    int insert(DdJobfrom record);

    int insertSelective(DdJobfrom record);

    DdJobfrom selectByPrimaryKey(Long jobfromid);

    int updateByPrimaryKeySelective(DdJobfrom record);

    int updateByPrimaryKey(DdJobfrom record);

    List<DdJobfrom> selectByJob(Long jobid);

    List<Map<String,Object>> selectByJobapi(Long jobid);
}