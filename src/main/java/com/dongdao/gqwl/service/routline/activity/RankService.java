package com.dongdao.gqwl.service.routline.activity;


import com.dongdao.gqwl.mapper.routline.activity.DdRankMapper;
import com.dongdao.gqwl.model.routline.activity.DdRank;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RankService<T> extends BaseService<T> {

    @Autowired
    private DdRankMapper<DdRank> ddAuditMapper;

    public DdRankMapper<DdRank> getMapper(){
        return ddAuditMapper;
    }

    public DdRank selectById(Long r_uid){
        return ddAuditMapper.selectById(r_uid);
    }

    public List<Map> selectByAct(Long actid){
        return ddAuditMapper.selectByAct(actid);
    }
}
