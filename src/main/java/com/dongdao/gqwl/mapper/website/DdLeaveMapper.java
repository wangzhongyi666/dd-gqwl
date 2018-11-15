package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.DdLeave;

public interface DdLeaveMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer leave_id);

    int insert(DdLeave record);

    int insertSelective(DdLeave record);

    DdLeave selectByPrimaryKey(Integer leave_id);

    int updateByPrimaryKeySelective(DdLeave record);

    int updateByPrimaryKey(DdLeave record);
}