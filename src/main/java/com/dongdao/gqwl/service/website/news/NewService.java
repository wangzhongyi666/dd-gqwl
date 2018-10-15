package com.dongdao.gqwl.service.website.news;

import com.dongdao.gqwl.mapper.website.news.DdNewsMapper;
import com.dongdao.gqwl.mapper.website.news.DdNewstypeMapper;
import com.dongdao.gqwl.model.website.news.DdNews;
import com.dongdao.gqwl.model.website.news.DdNewstype;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewService<T> extends BaseService<T> {

    @Autowired
    private DdNewsMapper<DdNews> ddStypeMapper;

    public DdNewsMapper<DdNews> getMapper() {
        return ddStypeMapper;
    }



}
