package com.dongdao.gqwl.service.website.job;

import com.dongdao.gqwl.mapper.website.job.DdJobtypeMapper;
import com.dongdao.gqwl.mapper.website.news.DdNewstypeMapper;
import com.dongdao.gqwl.model.website.job.DdJobtype;
import com.dongdao.gqwl.model.website.news.DdNewstype;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobtypeService<T> extends BaseService<T> {

    @Autowired
    private DdJobtypeMapper<DdJobtype> ddStypeMapper;

    public DdJobtypeMapper<DdJobtype> getMapper() {
        return ddStypeMapper;
    }



}
