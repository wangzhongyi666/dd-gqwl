package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.mapper.source.DdLabelMapper;
import com.dongdao.gqwl.model.source.DdLabel;
import com.dongdao.gqwl.model.source.DdStype;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService<T> extends BaseService<T> {

    @Autowired
    private DdLabelMapper<DdLabel> ddStypeMapper;

    public DdLabelMapper<DdLabel> getMapper() {
        return ddStypeMapper;
    }

    public List<DdStype> queryByList(DdLabel model){
        return ddStypeMapper.queryByList(model);
    }
}
