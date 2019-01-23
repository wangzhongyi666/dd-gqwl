package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.SysRole;
import com.dongdao.gqwl.mapper.SysRoleMapper;
import com.dongdao.gqwl.model.SysRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleService<T> extends BaseService<T> {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public SysRoleMapper<T> getMapper() {
        return sysRoleMapper;
    }

    public List<SysRole> queryAllRole(){
        return getMapper().queryAllRole();
    }

    public List<SysRole> queryByList(SysRoleModel model){
        return getMapper().queryByList(model);
    }

    public Integer queryByCount(SysRoleModel model){
        return getMapper().queryByCount(model);
    }

    public Integer deleteById(@Param("id")Integer id){
        return getMapper().deleteById(id);
    }
    public void deleteRelByRoleId(@Param("roleId")Integer roleId){
        getMapper().deleteRelByRoleId(roleId);
    }

    public int updateRole(SysRoleModel model){
       return getMapper().updateRole(model);
    }
}
