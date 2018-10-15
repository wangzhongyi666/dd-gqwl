package com.dongdao.gqwl.service.website;

import com.dongdao.gqwl.mapper.source.DdAuditMapper;
import com.dongdao.gqwl.mapper.website.DdProfileMapper;
import com.dongdao.gqwl.model.source.DdAudit;
import com.dongdao.gqwl.model.website.DdProfile;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService<T> extends BaseService<T> {

    @Autowired
    private DdProfileMapper<DdProfile> ddAuditMapper;

    public DdProfileMapper<DdProfile> getMapper(){
        return ddAuditMapper;
    }
}
