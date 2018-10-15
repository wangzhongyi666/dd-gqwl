package com.dongdao.gqwl.service.website.job;

import com.dongdao.gqwl.mapper.website.job.DdJobMapper;
import com.dongdao.gqwl.mapper.website.news.DdNewsMapper;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.model.website.news.DdNews;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService<T> extends BaseService<T> {

    @Autowired
    private DdJobMapper<DdJob> ddStypeMapper;

    public DdJobMapper<DdJob> getMapper() {
        return ddStypeMapper;
    }



}
