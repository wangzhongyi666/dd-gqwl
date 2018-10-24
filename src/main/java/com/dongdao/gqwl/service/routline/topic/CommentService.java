package com.dongdao.gqwl.service.routline.topic;

import com.dongdao.gqwl.mapper.routline.topic.DdCardconMapper;
import com.dongdao.gqwl.mapper.routline.topic.DdCommentMapper;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdComment;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService<T> extends BaseService<T> {

    @Autowired
    private DdCommentMapper<DdComment> ddAuditMapper;

    public DdCommentMapper<DdComment> getMapper(){
        return ddAuditMapper;
    }
}
