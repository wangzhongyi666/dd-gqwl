package com.dongdao.gqwl.mapper.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.website.Ddbrowse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DdbrowseMapper<T> extends BaseMapper {
    int deleteByPrimaryKey(Integer browse_id);

    int insert(Ddbrowse record);

    int insertSelective(Ddbrowse record);

    Ddbrowse selectByPrimaryKey(Integer browse_id);

    int updateByPrimaryKeySelective(Ddbrowse record);

    int updateByPrimaryKey(Ddbrowse record);

    List<Map> queryByBrowse(Integer user_id);

    Map queryByBrowseInfo(Integer browse_id);

    List<Map> queryByCards(@Param("r_uid") Integer u_rid);

    Map queryByCardsInfo(@Param("topid")Integer topid,@Param("cardid") Integer cardid);

    void updateByCardsId(@Param("isdelete")Integer isdelete,@Param("cardid") Integer cardid);
}