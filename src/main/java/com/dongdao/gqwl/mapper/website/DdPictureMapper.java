package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.DdPicture;

public interface DdPictureMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long picid);

    int insert(DdPicture record);

    int insertSelective(DdPicture record);

    DdPicture selectByPrimaryKey(Long picid);

    int updateByPrimaryKeySelective(DdPicture record);

    int updateByPrimaryKey(DdPicture record);
}