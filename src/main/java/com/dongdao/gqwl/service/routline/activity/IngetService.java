package com.dongdao.gqwl.service.routline.activity;

import com.dongdao.gqwl.mapper.routline.activity.DdIngetMapper;
import com.dongdao.gqwl.model.routline.activity.DdInget;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngetService<T> extends BaseService<T> {

    @Autowired
    private DdIngetMapper<DdInget> ddAuditMapper;

    public DdIngetMapper<DdInget> getMapper(){
        return ddAuditMapper;
    }
}
