package com.dongdao.gqwl.mapper.routline.activity;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.activity.DdActivity;

import java.util.List;
import java.util.Map;

public interface DdActivityMapper<T>  extends BaseMapper {
    int deleteByPrimaryKey(Long actid);

    int insert(DdActivity record);

    int insertSelective(DdActivity record);

    DdActivity selectByPrimaryKey(Long actid);

    int updateByPrimaryKeySelective(DdActivity record);

    int updateByPrimaryKey(DdActivity record);

    List<Map> selectBybanners();


}