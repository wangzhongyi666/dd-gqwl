package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.mapper.source.DdStypeMapper;
import com.dongdao.gqwl.model.source.DdStype;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class STypeService<T> extends BaseService<T> {

    @Autowired
    private DdStypeMapper<DdStype> ddStypeMapper;

    public DdStypeMapper<DdStype> getMapper() {
        return ddStypeMapper;
    }

    public List<DdStype> queryByList(DdStype model){
        return ddStypeMapper.queryByList(model);
    }
}
