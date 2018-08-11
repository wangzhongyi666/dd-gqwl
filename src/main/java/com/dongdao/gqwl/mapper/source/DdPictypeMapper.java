package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.BaseModel;
import com.dongdao.gqwl.model.source.DdPictype;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface  DdPictypeMapper<DdPictype> extends BaseMapper {
    int deleteByPrimaryKey(Long pictypeid);

    int insert(DdPictype record);

    int insertSelective(DdPictype record);

    DdPictype selectByPrimaryKey(Long pictypeid);

    int updateByPrimaryKeySelective(DdPictype record);

    int updateByPrimaryKey(DdPictype record);

   }