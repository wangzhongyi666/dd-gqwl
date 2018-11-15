package com.dongdao.gqwl.service.website.job;

import com.dongdao.gqwl.mapper.website.job.DdJob_fromMapper;
import com.dongdao.gqwl.model.website.job.DdJob_from;
import com.dongdao.gqwl.model.website.job.DdJobfrom;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Job_fromService<T> extends BaseService<T> {

    @Autowired
    private DdJob_fromMapper<DdJob_from> ddStypeMapper;

    public DdJob_fromMapper<DdJob_from> getMapper() {
        return ddStypeMapper;
    }

    public List<DdJob_from> selectByJob(Long jobid){
        return getMapper().selectByJob(jobid);
    }

    public DdJob_from selectByJobs(DdJob_from record){
        return getMapper().selectByJobs(record);
    }
}
