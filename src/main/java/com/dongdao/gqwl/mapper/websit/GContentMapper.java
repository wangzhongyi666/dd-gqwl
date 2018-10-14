package com.dongdao.gqwl.mapper.websit;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.websit.GContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GContentMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GContent record);

    int insertSelective(GContent record);

    GContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GContent record);

    int updateByPrimaryKey(GContent record);
}