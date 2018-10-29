package com.dongdao.gqwl.service.website.news;

import com.dongdao.gqwl.mapper.website.news.DdInformationMapper;
import com.dongdao.gqwl.mapper.website.news.DdNewsMapper;
import com.dongdao.gqwl.model.website.job.DdInformation;
import com.dongdao.gqwl.model.website.news.DdNews;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationService<T> extends BaseService<T> {

    @Autowired
    private DdInformationMapper<DdInformation> ddInformationMapper;

    public DdInformationMapper<DdInformation> getMapper() {
        return ddInformationMapper;
    }



}
