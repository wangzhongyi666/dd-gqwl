package com.dongdao.gqwl.mapper.website.news;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.job.DdInformation;

public interface DdInformationMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer information_id);

    int insert(DdInformation record);

    int insertSelective(DdInformation record);

    DdInformation selectByPrimaryKey(Integer information_id);

    int updateByPrimaryKeySelective(DdInformation record);

    int updateByPrimaryKey(DdInformation record);
}