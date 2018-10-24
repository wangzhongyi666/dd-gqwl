package com.dongdao.gqwl.service.routline.topic;

import com.dongdao.gqwl.mapper.routline.topic.DdCardsMapper;
import com.dongdao.gqwl.mapper.routline.topic.DdTopicMapper;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.model.routline.topic.DdTopic;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardsService<T> extends BaseService<T> {

    @Autowired
    private DdCardsMapper<DdCards> ddAuditMapper;

    public DdCardsMapper<DdCards> getMapper(){
        return ddAuditMapper;
    }

    public List<DdCardcon> selectCfile(Long cardid){
        return getMapper().selectCfile(cardid);
    }
}
