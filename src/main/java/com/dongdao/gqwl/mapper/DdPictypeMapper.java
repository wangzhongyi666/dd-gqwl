package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.model.DdPictype;

public interface DdPictypeMapper extends BaseMapper{
    int deleteByPrimaryKey(Long pictypeid);

    int insert(DdPictype record);

    int insertSelective(DdPictype record);

    DdPictype selectByPrimaryKey(Long pictypeid);

    int updateByPrimaryKeySelective(DdPictype record);

    int updateByPrimaryKey(DdPictype record);
}