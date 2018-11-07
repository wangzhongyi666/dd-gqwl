package com.dongdao.gqwl.service.routline.activity;

import com.dongdao.gqwl.mapper.routline.activity.DdIngetMapper;
import com.dongdao.gqwl.model.routline.activity.DdInget;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngetService<T> extends BaseService<T> {

    @Autowired
    private DdIngetMapper<DdInget> ddAuditMapper;

    public DdIngetMapper<DdInget> getMapper(){
        return ddAuditMapper;
    }

    public DdInget selectByUser(Long r_uid){
        return getMapper().selectByUser(r_uid);
    }
    public List<DdInget> selectByToday(){
        return getMapper().selectByToday();
    }
}
