package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.Ddbrowse;

public interface DdbrowseMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer browse_id);

    int insert(Ddbrowse record);

    int insertSelective(Ddbrowse record);

    Ddbrowse selectByPrimaryKey(Integer browse_id);

    int updateByPrimaryKeySelective(Ddbrowse record);

    int updateByPrimaryKey(Ddbrowse record);
}