package com.dongdao.gqwl.service.website.job;

import com.dongdao.gqwl.mapper.website.job.DdJobMapper;
import com.dongdao.gqwl.mapper.website.job.DdResumeMapper;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.model.website.job.DdResume;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeService<T> extends BaseService<T> {

    @Autowired
    private DdResumeMapper<DdResume> ddStypeMapper;

    public DdResumeMapper<DdResume> getMapper() {
        return ddStypeMapper;
    }



}
