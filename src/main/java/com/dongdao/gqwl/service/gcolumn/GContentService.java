package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.website.GContentMapper;
import com.dongdao.gqwl.model.website.GSeo;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GContentService<T> extends BaseService<T> {
    @Autowired
    private GContentMapper<GSeo> gContentMapper;

    public GContentMapper<GSeo> getMapper() {
        return gContentMapper;
    }

}
