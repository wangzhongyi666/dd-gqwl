package com.dongdao.gqwl.service.routline.activity;

import com.dongdao.gqwl.mapper.routline.activity.DdActivityMapper;
import com.dongdao.gqwl.mapper.routline.topic.DdCardconMapper;
import com.dongdao.gqwl.model.routline.activity.DdActivity;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService<T> extends BaseService<T> {

    @Autowired
    private DdActivityMapper<DdActivity> ddAuditMapper;

    public DdActivityMapper<DdActivity> getMapper(){
        return ddAuditMapper;
    }

    public List<DdActivity> selectById(Long type){
        return getMapper().selectById(type);
    }
}
