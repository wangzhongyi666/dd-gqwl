package com.e365.flexiblebe.model;

import com.e365.flexiblebe.bean.BaseBean;

public class SysRoleModel extends BaseModel{

    private Integer id;
    private String jname;

    private String jsign;
    private String jsubname;

    private Integer jlevel;
    private String create_time;
    private String update_time;
    private Integer objId;
    private Integer relType;


    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public Integer getRelType() {
        return relType;
    }

    public void setRelType(Integer relType) {
        this.relType = relType;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getJsign() {
        return jsign;
    }

    public void setJsign(String jsign) {
        this.jsign = jsign;
    }

    public String getJsubname() {
        return jsubname;
    }

    public void setJsubname(String jsubname) {
        this.jsubname = jsubname;
    }

    public Integer getJlevel() {
        return jlevel;
    }

    public void setJlevel(Integer jlevel) {
        this.jlevel = jlevel;
    }
}
