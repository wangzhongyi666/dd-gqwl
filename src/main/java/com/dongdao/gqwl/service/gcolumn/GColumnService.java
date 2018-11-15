package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.website.GColumnMapper;
import com.dongdao.gqwl.model.website.GColumn;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GColumnService<T> extends BaseService<T> {
    @Autowired
    private GColumnMapper<GColumn> gColumnMapper;

    public GColumnMapper<GColumn> getMapper() {
        return gColumnMapper;
    }

}
