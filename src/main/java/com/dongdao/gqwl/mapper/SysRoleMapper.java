package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SysRole;
import com.dongdao.gqwl.model.SysRoleModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRoleMapper<T> extends BaseMapper<T>{

    @Select("select * FROM sys_role r")
    List<SysRole> queryAllRole();

    @Select("<script>select t.* from sys_role t where 1=1 "+
            "<if test=\"jname != null and jname != ''\"> and jname like CONCAT('%', #{jname}, '%')</if>" +
            "order by t.id desc " +
            "<if test=\"num1 != null and num2 != null\">limit #{num1},#{num2}</if> " +
            "</script>")
    List<SysRole> queryByList(SysRoleModel model);

    @Select("<script>select count(0) from sys_role t  where 1=1 "+
            "<if test=\"jname != null and jname != ''\"> and jname like CONCAT('%', #{jname}, '%')</if>" +
            "</script>")
    Integer queryByCount(SysRoleModel model);

    @Delete("delete from sys_role where id = #{id}")
    Integer deleteById(@Param("id")Integer id);

    @Delete("delete from sys_role_rel where roleId = #{roleId} and relType=0")
    void deleteRelByRoleId(@Param("roleId")Integer roleId);

}
