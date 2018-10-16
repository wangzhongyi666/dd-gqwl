package com.dongdao.gqwl.mapper.websit;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.websit.RasteMassage;

public interface RasteMassageMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer massage_id);

    int insert(RasteMassage record);

    int insertSelective(RasteMassage record);

    RasteMassage selectByPrimaryKey(Integer massage_id);

    int updateByPrimaryKeySelective(RasteMassage record);

    int updateByPrimaryKey(RasteMassage record);
}