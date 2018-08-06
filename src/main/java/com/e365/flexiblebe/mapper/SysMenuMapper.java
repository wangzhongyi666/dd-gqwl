package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.SysMenu;
import com.e365.flexiblebe.model.SysRoleModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysMenuMapper<T> extends BaseMapper<T> {

    /**
     * 获取顶级菜单
     * @return
     */
    @Select("<script>select id,name,url,parentId,actions,imgclass from sys_menu where deleted= 0 and parentId is null<if test=\"menuId !=null \">and id != #{menuId}</if> order by rank</script>")
    public List<SysMenu> getRootMenu(@Param("menuId")Integer menuId);

    /**
     * 获取子菜单
     * @return
     */
    @Select("select id,name,url,parentId,actions,imgclass from sys_menu where deleted= 0 and parentId is not null order by rank")
    public List<SysMenu> getChildMenu();

    /**
     * 根据用户id查询父菜单菜单
     * @param userId
     * @return
     */
    @Select(" SELECT DISTINCT id,NAME,url,parentId,actions,imgclass " +
            " FROM sys_menu m " +
            " WHERE deleted= 0 AND parentId IS NULL " +
            " AND EXISTS ( " +
            " SELECT * FROM sys_role_rel r " +
            " WHERE r.objId = m.id AND r.relType = 0 " +
            " AND EXISTS ( " +
            " SELECT 1 FROM sys_role_rel u WHERE u.roleId = r.roleId  AND u.relType = 1 AND u.objId = #{userId} " +
            ") " +
            ") order by rank")
    List<SysMenu> getRootMenuByUser(@Param("userId")Integer userId);

    @Select(" SELECT DISTINCT id,NAME,url,parentId,actions,rank,imgclass " +
            " FROM sys_menu m " +
            " WHERE deleted= 0 AND parentId IS NOT NULL " +
            " AND EXISTS ( " +
            " SELECT * FROM sys_role_rel r " +
            " WHERE r.objId = m.id AND r.relType = 0 " +
            " AND EXISTS ( " +
            " SELECT 1 FROM sys_role_rel u WHERE u.roleId = r.roleId  AND u.relType = 1 AND u.objId = #{userId} " +
            " )" +
            " ) " +
            " order by rank")
    List<SysMenu> getChildMenuByUser(@Param("userId")Integer userId);

    @Select("<script>select id,name,url,parentId,actions,imgclass from sys_menu</script>")
    List<SysMenu> getAllMenu();
    @Select("<script>select id,name,url,parentId,actions,imgclass from sys_menu</script>")
    List<SysMenu> getButn();
    @Select("<script>select objId from sys_role_rel where relType=0 and roleId=#{roleId}</script>")
    List<Integer> getIdByRoleId(@Param("roleId")Integer roleId);

    @Insert("<script>insert into sys_role (jname,jdesc,create_time,update_time) values(#{jname},#{jname},#{create_time},#{update_time})</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addRole(SysRoleModel model);
    @Insert("<script>insert into sys_role_rel (roleId,objId,relType) values(#{id},#{objId},#{relType})</script>")
    void addRoleRel(SysRoleModel model);
}
