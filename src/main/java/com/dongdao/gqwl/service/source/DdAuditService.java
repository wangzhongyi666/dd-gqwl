package com.dongdao.gqwl.service.source;

import com.dongdao.gqwl.mapper.source.DdAuditMapper;
import com.dongdao.gqwl.model.source.DdAudit;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdAuditService<T> extends BaseService<T> {

    @Autowired
    private DdAuditMapper<DdAudit> ddAuditMapper;

    public DdAuditMapper<DdAudit> getMapper(){
        return ddAuditMapper;
    }
}
