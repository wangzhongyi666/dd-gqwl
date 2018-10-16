package com.dongdao.gqwl.service.gcolumn;

import com.dongdao.gqwl.mapper.websit.DdLeaveMapper;
import com.dongdao.gqwl.mapper.websit.RasteMassageMapper;
import com.dongdao.gqwl.model.websit.DdLeave;
import com.dongdao.gqwl.model.websit.RasteMassage;
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

