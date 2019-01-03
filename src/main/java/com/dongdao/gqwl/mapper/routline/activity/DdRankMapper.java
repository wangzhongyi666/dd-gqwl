package com.dongdao.gqwl.mapper.routline.activity;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.routline.activity.DdRank;

import java.util.List;
import java.util.Map;

public interface DdRankMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Long randid);

    int insert(DdRank record);

    int insertSelective(DdRank record);

    DdRank selectByPrimaryKey(Long randid);

    int updateByPrimaryKeySelective(DdRank record);

    int updateByPrimaryKey(DdRank record);

    DdRank selectById(Long r_uid);

    List<Map> selectByAct(Long actid);

    int selectRank(Long r_uid);

    List<Map> selectRuid(Long r_uid);

    List<Map> selectBefor(DdRank record);

    List<Map> selectAfter(DdRank record);
}