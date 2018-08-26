package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.mapper.source.DdScontentMapper;
import com.dongdao.gqwl.model.source.DdScontent;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScontentService<T> extends BaseService<T> {

    @Autowired
    private DdScontentMapper<DdScontent> ddStypeMapper;

    public DdScontentMapper<DdScontent> getMapper() {
        return ddStypeMapper;
    }

    public List<DdScontent> queryByList(DdScontent model){
        return ddStypeMapper.queryByList(model);
    }
}
