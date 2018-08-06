package com.e365.flexiblebe.bean;

public class SysRoleRel extends BaseBean {
    private Integer roleId;//角色主键 sys_role.id
    private Integer objId;//关联主键 type=0管理sys_menu.id, type=1关联sys_menu_btn.id
    private Integer relType;//关联类型 0=菜单,1=用户
}
