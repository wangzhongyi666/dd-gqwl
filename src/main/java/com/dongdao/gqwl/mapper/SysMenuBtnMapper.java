package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuBtnMapper<T> extends BaseMapper<T>{

    /**
     * 根据用户id查询按钮
     * @param userId
     * @return
     */
    @Select(" select * from sys_role_rel where level=3")
    public List<SysMenu> getMenuBtnByUser(@Param("userId")Integer userId);
}
