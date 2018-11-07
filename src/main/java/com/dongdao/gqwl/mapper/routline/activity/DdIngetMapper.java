package com.dongdao.gqwl.mapper.routline.activity;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.activity.DdInget;

import java.util.List;

public interface DdIngetMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long ingetid);

    int insert(DdInget record);

    int insertSelective(DdInget record);

    DdInget selectByPrimaryKey(Long ingetid);

    int updateByPrimaryKeySelective(DdInget record);

    int updateByPrimaryKey(DdInget record);

    DdInget selectByUser(Long r_uid);

    List<DdInget> selectByToday();
}