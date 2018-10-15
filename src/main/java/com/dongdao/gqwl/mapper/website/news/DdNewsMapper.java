package com.dongdao.gqwl.mapper.website.news;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.news.DdNews;

public interface DdNewsMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long newsid);

    int insert(DdNews record);

    int insertSelective(DdNews record);

    DdNews selectByPrimaryKey(Long newsid);

    int updateByPrimaryKeySelective(DdNews record);

    int updateByPrimaryKeyWithBLOBs(DdNews record);

    int updateByPrimaryKey(DdNews record);
}