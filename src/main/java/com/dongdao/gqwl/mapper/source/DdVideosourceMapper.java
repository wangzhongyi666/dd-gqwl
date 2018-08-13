package com.dongdao.gqwl.mapper.source;

import com.dongdao.gqwl.model.source.DdVideosource;

public interface DdVideosourceMapper {
    int deleteByPrimaryKey(Long videoid);

    int insert(DdVideosource record);

    int insertSelective(DdVideosource record);

    DdVideosource selectByPrimaryKey(Long videoid);

    int updateByPrimaryKeySelective(DdVideosource record);

    int updateByPrimaryKey(DdVideosource record);
}