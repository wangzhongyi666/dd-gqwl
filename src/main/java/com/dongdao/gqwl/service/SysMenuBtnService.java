package com.dongdao.gqwl.service;

import com.dongdao.gqwl.bean.SysMenu;
import com.dongdao.gqwl.mapper.SysMenuBtnMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuBtnService<T> extends BaseService<T> {

    @Autowired
    private SysMenuBtnMapper<T> sysMenuBtnMapper;

    public SysMenuBtnMapper<T> getMapper(){
        return sysMenuBtnMapper;
    }

    /**
     * 根据用户id查询按钮
     * @param userId
     * @return
     */
    public List<SysMenu> getMenuBtnByUser(@Param("userId")Integer userId){
        return getMapper().getMenuBtnByUser(userId);
    }
}
