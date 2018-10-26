package com.dongdao.gqwl.service.routline.topic;

import com.dongdao.gqwl.mapper.routline.topic.DdCardconMapper;
import com.dongdao.gqwl.mapper.routline.topic.DdCardsMapper;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardconService<T> extends BaseService<T> {

    @Autowired
    private DdCardconMapper<DdCardcon> ddAuditMapper;

    public DdCardconMapper<DdCardcon> getMapper(){
        return ddAuditMapper;
    }
}
