package com.dongdao.gqwl.mapper.webinfo;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.webinfo.DdWebinfo;

public interface DdWebinfoMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long webid);

    int insert(DdWebinfo record);

    int insertSelective(DdWebinfo record);

    DdWebinfo selectByPrimaryKey(Long webid);

    int updateByPrimaryKeySelective(DdWebinfo record);

    int updateByPrimaryKey(DdWebinfo record);
}