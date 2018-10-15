package com.dongdao.gqwl.service.website.news;

import com.dongdao.gqwl.mapper.website.news.DdNewstypeMapper;
import com.dongdao.gqwl.model.website.news.DdNewstype;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewstypeService<T> extends BaseService<T> {

    @Autowired
    private DdNewstypeMapper<DdNewstype> ddStypeMapper;

    public DdNewstypeMapper<DdNewstype> getMapper() {
        return ddStypeMapper;
    }



}
