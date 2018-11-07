package com.dongdao.gqwl.mapper.website.job;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.job.DdJob_from;
import com.dongdao.gqwl.model.website.job.DdJobfrom;

import java.util.List;

public interface DdJob_fromMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long job_fromid);

    int insert(DdJob_from record);

    int insertSelective(DdJob_from record);

    DdJob_from selectByPrimaryKey(Long job_fromid);

    int updateByPrimaryKeySelective(DdJob_from record);

    int updateByPrimaryKey(DdJob_from record);

    List<DdJob_from> selectByJob(Long jobid);

    DdJob_from selectByJobs(DdJob_from record);
}