package com.dongdao.gqwl.service.routline.topic;

import com.dongdao.gqwl.mapper.routline.topic.DdTopicMapper;
import com.dongdao.gqwl.mapper.routline.topic.DdZrecordMapper;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.model.routline.topic.DdZrecord;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZrecordService<T> extends BaseService<T> {

    @Autowired
    private DdZrecordMapper<DdZrecord> ddAuditMapper;

    public DdZrecordMapper<DdZrecord> getMapper(){
        return ddAuditMapper;
    }

    public DdZrecord selectById(DdZrecord record){
        return getMapper().selectById(record);
    }

    public int selectByCard(DdZrecord zrecord){
        return getMapper().selectByCard(zrecord);
    }
}
