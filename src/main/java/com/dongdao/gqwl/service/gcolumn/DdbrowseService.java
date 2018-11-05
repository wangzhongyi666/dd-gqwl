package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.website.DdbrowseMapper;
import com.dongdao.gqwl.model.website.Ddbrowse;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdbrowseService<T> extends BaseService<T> {
    @Autowired
    private DdbrowseMapper<Ddbrowse> ddbrowseMapper;

    public DdbrowseMapper<Ddbrowse> getMapper() {
        return ddbrowseMapper;
    }

}
