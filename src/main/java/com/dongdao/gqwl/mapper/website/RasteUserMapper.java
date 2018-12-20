package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.RasteUser;

public interface RasteUserMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RasteUser record);

    int insertSelective(RasteUser record);

    RasteUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RasteUser record);

    int updateByPrimaryKey(RasteUser record);

    RasteUser queryByToLogin(RasteUser user);

    Integer updateByWxIdent(RasteUser user);

}