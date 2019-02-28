package com.dongdao.gqwl.service.website;

import com.dongdao.gqwl.mapper.website.DdPartnerMapper;
import com.dongdao.gqwl.mapper.website.news.DdNewsMapper;
import com.dongdao.gqwl.model.website.DdPartner;
import com.dongdao.gqwl.model.website.news.DdNews;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerService<T> extends BaseService<T> {

    @Autowired
    private DdPartnerMapper<DdPartner> ddStypeMapper;

    public DdPartnerMapper<DdPartner> getMapper() {
        return ddStypeMapper;
    }



}
