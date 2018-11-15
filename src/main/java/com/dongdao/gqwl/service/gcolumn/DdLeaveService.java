package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.website.DdLeaveMapper;
import com.dongdao.gqwl.model.website.DdLeave;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdLeaveService<T> extends BaseService<T> {
    @Autowired
    private DdLeaveMapper<DdLeave> ddLeaveMapper;

    public DdLeaveMapper<DdLeave> getMapper() {
        return ddLeaveMapper;
    }

}

