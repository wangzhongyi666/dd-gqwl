package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SysMenu;
import com.dongdao.gqwl.model.SysRoleModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysMenuMapper<T> extends BaseMapper<T> {

    /**
     * 获取顶级菜单
     * @return
     */
    @Select("<script>select id,name,url,parentId,actions,imgclass from sys_menu where deleted= 0 and parentId is null<if test=\"menuId !=null \">and id != #{menuId}</if> order by rank</script>")
    List<SysMenu> getRootMenu(@Param("menuId") Integer menuId);

    /**
     * 获取子菜单
     * @return
     */
    @Select("select id,name,url,parentId,actions,imgclass from sys_menu where deleted= 0 and parentId is not null order by rank")
    List<SysMenu> getChildMenu();

    /**
     * 根据用户id查询父菜单菜单
     * @param userId
     * @return
     */
    @Select(" SELECT DISTINCT id,NAME,url,parentId,actions,imgclass " +
            " FROM sys_menu m " +
            " LEFT JOIN sys_role_rel u on m.id = u.objId " +
            " WHERE deleted= 0 AND parentId IS NULL " +
            " AND m.type=1 and u.roleId = #{role_id} order by rank")
    List<SysMenu> getRootMenuByUser(@Param("role_id")Integer role_id);

    @Select(" SELECT DISTINCT id,NAME,url,parentId,actions,imgclass " +
            " FROM sys_menu m " +
            " LEFT JOIN sys_role_rel u on m.id = u.objId " +
            " WHERE deleted= 0 AND parentId IS NOT NULL " +
            " AND m.type=2 and u.roleId = #{role_id} order by rank")
    List<SysMenu> getChildMenuByUser(@Param("role_id")Integer role_id);


        @Select("<script>SELECT DISTINCT id,NAME,url,parentId,actions,imgclass " +
                " FROM sys_menu m " +
                " LEFT JOIN sys_role_rel u on m.id = u.objId " +
                " WHERE deleted= 0 AND parentId IS NOT NULL " +
                " AND m.type=3 " +
                " <if test=\"role_id != null\"> and u.roleId = #{role_id} </if>" +
                " order by rank</script>")
    List<SysMenu> getChildMenuBtnByUser(@Param("role_id")Integer role_id);

    @Select("<script>select id,name,url,parentId,actions,imgclass from sys_menu where deleted = 0</script>")
    List<SysMenu> getAllMenu();
    @Select("<script>select id,name,url,parentId,actions,imgclass from sys_menu where deleted = 0</script>")
    List<SysMenu> getButn();
    @Select("<script>select objId from sys_role_rel where relType=0 and roleId=#{roleId}</script>")
    List<Integer> getIdByRoleId(@Param("roleId")Integer roleId);

    @Insert("<script>insert into sys_role (jname,jdesc,create_time,update_time) " +
            "values(#{jname},#{jname},#{create_time},#{update_time})</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addRole(SysRoleModel model);
    @Insert("<script>insert into sys_role_rel (roleId,objId,relType) values(#{id},#{objId},#{relType})</script>")
    void addRoleRel(SysRoleModel model);
}
