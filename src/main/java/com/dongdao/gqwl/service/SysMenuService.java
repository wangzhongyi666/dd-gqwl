package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.SysMenu;
import com.dongdao.gqwl.mapper.SysMenuMapper;
import com.dongdao.gqwl.model.SysRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysMenuService<T> extends BaseService<T> {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    public SysMenuMapper<T> getMapper() {
        return sysMenuMapper;
    }

    /**
     * 获取顶级菜单
     * @return
     */
    public List<SysMenu> getRootMenu(@Param("menuId")Integer menuId){
        return sysMenuMapper.getRootMenu(menuId);
    }

    /**
     * 获取子菜单
     * @return
     */
    public List<SysMenu> getChildMenu(){
        return sysMenuMapper.getChildMenu();
    }

    /**
     * 根据用户id查询父菜单菜单
     * @param userId
     * @return
     */
    public List<SysMenu> getRootMenuByUser(@Param("userId")Integer userId){
        return sysMenuMapper.getRootMenuByUser(userId);
    }

    public List<SysMenu> getChildMenuByUser(@Param("userId")Integer userId){
        return sysMenuMapper.getChildMenuByUser(userId);
    }
    public List<SysMenu> getAllMenu(){
        return sysMenuMapper.getAllMenu();
    }
    public List<SysMenu> getButn(){
        return sysMenuMapper.getButn();
    }
    public List<Integer> getIdByRoleId(@Param("roleId")Integer roleId){
        return sysMenuMapper.getIdByRoleId(roleId);
    }
    public void addRole(SysRoleModel model){
        sysMenuMapper.addRole(model);
    }
    public void addRoleRel(SysRoleModel model){
        sysMenuMapper.addRoleRel(model);
    }
}
