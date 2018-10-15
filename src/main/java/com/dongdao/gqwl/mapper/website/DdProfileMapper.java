package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.DdProfile;

import java.util.List;


public interface DdProfileMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long proid);

    int insert(DdProfile record);

    int insertSelective(DdProfile record);

    DdProfile selectByPrimaryKey(Long proid);

    int updateByPrimaryKeySelective(DdProfile record);

    int updateByPrimaryKeyWithBLOBs(DdProfile record);

    int updateByPrimaryKey(DdProfile record);


}