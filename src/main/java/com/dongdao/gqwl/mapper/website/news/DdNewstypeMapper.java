package com.dongdao.gqwl.mapper.website.news;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.news.DdNewstype;

public interface DdNewstypeMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long newstypeid);

    int insert(DdNewstype record);

    int insertSelective(DdNewstype record);

    DdNewstype selectByPrimaryKey(Long newstypeid);

    int updateByPrimaryKeySelective(DdNewstype record);

    int updateByPrimaryKey(DdNewstype record);
}