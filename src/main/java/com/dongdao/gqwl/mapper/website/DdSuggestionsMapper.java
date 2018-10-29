package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.DdSuggestions;

public interface DdSuggestionsMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer suggestion_id);

    int insert(DdSuggestions record);

    int insertSelective(DdSuggestions record);

    DdSuggestions selectByPrimaryKey(Integer suggestion_id);

    int updateByPrimaryKeySelective(DdSuggestions record);

    int updateByPrimaryKey(DdSuggestions record);
}