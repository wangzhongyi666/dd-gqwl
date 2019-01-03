package com.dongdao.gqwl.service.routline.activity;


import com.dongdao.gqwl.mapper.routline.activity.DdRactMapper;
import com.dongdao.gqwl.mapper.routline.activity.DdRankMapper;
import com.dongdao.gqwl.model.routline.activity.DdRact;
import com.dongdao.gqwl.model.routline.activity.DdRank;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RactService<T> extends BaseService<T> {

    @Autowired
    private DdRactMapper<DdRact> ddAuditMapper;

    public DdRactMapper<DdRact> getMapper(){
        return ddAuditMapper;
    }

    public DdRact selectByPrimaryKey(DdRact record){
        return getMapper().selectByPrimaryKey(record);
    }
}
