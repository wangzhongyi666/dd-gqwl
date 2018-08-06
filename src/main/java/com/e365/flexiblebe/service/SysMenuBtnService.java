package com.e365.flexiblebe.service;

import com.e365.flexiblebe.bean.SysMenu;
import com.e365.flexiblebe.bean.SysMenuBtn;
import com.e365.flexiblebe.mapper.SysMenuBtnMapper;
import com.e365.flexiblebe.mapper.SysMenuMapper;
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
