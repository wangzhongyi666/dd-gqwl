package com.dongdao.gqwl.service.website.job;

import com.dongdao.gqwl.mapper.website.job.DdJobMapper;
import com.dongdao.gqwl.mapper.website.job.DdJobfromMapper;
import com.dongdao.gqwl.model.website.job.DdJob;
import com.dongdao.gqwl.model.website.job.DdJobfrom;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobfromService<T> extends BaseService<T> {

    @Autowired
    private DdJobfromMapper<DdJobfrom> ddStypeMapper;

    public DdJobfromMapper<DdJobfrom> getMapper() {
        return ddStypeMapper;
    }

    public List<DdJobfrom> selectByJob(Long jobid){
        return getMapper().selectByJob(jobid);
    }

    public List<Map<String,Object>> selectByJobapi(Long jobid){
        return getMapper().selectByJobapi(jobid);
    }
}
