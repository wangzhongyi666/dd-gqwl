package com.dongdao.gqwl.mapper.websit;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.websit.GSeo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GSeoMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GSeo record);

    int insertSelective(GSeo record);

    GSeo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GSeo record);

    int updateByPrimaryKey(GSeo record);
}