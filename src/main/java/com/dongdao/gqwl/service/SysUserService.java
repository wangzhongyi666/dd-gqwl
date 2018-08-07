package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.SysUserModel;
import com.dongdao.gqwl.mapper.SysUserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserService<T> extends BaseService<T>{

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserMapper<T> getMapper() {
        return sysUserMapper;
    }

    public List<SysUser> queryLogin(@Param("email")String email, @Param("pwd")String pwd){
        return sysUserMapper.queryLogin(email,pwd);
    }
    public Integer updateLogin(@Param("id")Integer id, @Param("loginCount")Integer loginCount, @Param("loginTime")Date loginTime){
        return sysUserMapper.updateLogin(id,loginCount,loginTime );
    }

    public String queryNameRole(@Param("id")Integer id){
        return sysUserMapper.queryNameRole(id);
    }

    public String queryDeptName(@Param("id")Integer id){
        return sysUserMapper.queryDeptName(id);
    }

    public SysUser queryById(@Param("id")Integer id){
        return sysUserMapper.queryById(id);
    }

    public List<SysUser> queryByList(SysUserModel model) throws Exception {
        return sysUserMapper.queryByList(model);
    }

    public Integer queryByCount(SysUserModel model){
        return sysUserMapper.queryByCount(model);
    }

    public Integer add(SysUserModel model){
        return sysUserMapper.add(model);
    }

    public Integer updatePwd(@Param("id")Integer id, @Param("pwd")String pwd){
        return getMapper().updatePwd(id, pwd);
    }

    public Integer delUser(@Param("id")Integer id){
        return getMapper().delUser(id);
    }
}
