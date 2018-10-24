package com.dongdao.gqwl.service.routline.topic;

import com.dongdao.gqwl.mapper.routline.topic.DdTopicMapper;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService<T> extends BaseService<T> {

    @Autowired
    private DdTopicMapper<DdTopic> ddAuditMapper;

    public DdTopicMapper<DdTopic> getMapper(){
        return ddAuditMapper;
    }
}
