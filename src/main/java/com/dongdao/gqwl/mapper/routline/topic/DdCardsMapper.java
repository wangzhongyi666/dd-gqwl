package com.dongdao.gqwl.mapper.routline.topic;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.topic.DdCardcon;
import com.dongdao.gqwl.model.routline.topic.DdCards;

import java.util.HashMap;
import java.util.List;

public interface DdCardsMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long cardid);

    int insert(DdCards record);

    int insertSelective(DdCards record);

    DdCards selectByPrimaryKey(Long cardid);

    int updateByPrimaryKeySelective(DdCards record);

    int updateByPrimaryKeyWithBLOBs(DdCards record);

    int updateByPrimaryKey(DdCards record);

    List<DdCardcon> selectCfile(Long cardid);

    HashMap<String,Object> selectById(DdCards record);
}