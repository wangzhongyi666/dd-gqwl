package com.dongdao.gqwl.bean;

import java.util.Date;

public class SysRole extends BaseBean{

    private Integer id;
    private String jname;
    private String jdesc;
    private String jsign;
    private String jsubname;

    private Integer jlevel;
    private Date create_time;
    private Date update_time;

    public String getJdesc() {
        return jdesc;
    }

    public void setJdesc(String jdesc) {
        this.jdesc = jdesc;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
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
