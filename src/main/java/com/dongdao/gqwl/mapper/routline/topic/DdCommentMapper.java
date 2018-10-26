package com.dongdao.gqwl.mapper.routline.topic;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.topic.DdComment;

public interface DdCommentMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long commentid);

    int insert(DdComment record);

    int insertSelective(DdComment record);

    DdComment selectByPrimaryKey(Long commentid);

    int updateByPrimaryKeySelective(DdComment record);

    int updateByPrimaryKeyWithBLOBs(DdComment record);

    int updateByPrimaryKey(DdComment record);
}