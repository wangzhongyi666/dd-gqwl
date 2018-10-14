package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.websit.GContentMapper;
import com.dongdao.gqwl.mapper.websit.GSeoMapper;
import com.dongdao.gqwl.model.websit.GContent;
import com.dongdao.gqwl.model.websit.GSeo;
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
