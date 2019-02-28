package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.DdPartner;

public interface DdPartnerMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long partner);

    int insert(DdPartner record);

    int insertSelective(DdPartner record);

    DdPartner selectByPrimaryKey(Long partner);

    int updateByPrimaryKeySelective(DdPartner record);

    int updateByPrimaryKey(DdPartner record);
}