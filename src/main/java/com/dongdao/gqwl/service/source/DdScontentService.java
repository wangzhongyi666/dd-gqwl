package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.mapper.source.DdScontentMapper;
import com.dongdao.gqwl.model.source.DdScontent;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdScontentService<T> extends BaseService<T> {

    @Autowired
    private DdScontentMapper<DdScontent> ddScontentMapper;

    public DdScontentMapper<DdScontent> getMapper() {
        return ddScontentMapper;
    }

}
