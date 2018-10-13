package com.dongdao.gqwl.model.webinfo;

import com.dongdao.gqwl.model.BaseModel;

public class DdWebinfo extends BaseModel {
    private Long webid;

    private String gname;

    private String gvalue;

    private String creattime;

    private String updatetime;

    private String w_uid;

    private Integer g_state;

    private String filed1;

    private Integer filed2;

    public Long getWebid() {
        return webid;
    }

    public void setWebid(Long webid) {
        this.webid = webid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname == null ? null : gname.trim();
    }

    public String getGvalue() {
        return gvalue;
    }

    public void setGvalue(String gvalue) {
        this.gvalue = gvalue == null ? null : gvalue.trim();
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getW_uid() {
        return w_uid;
    }

    public void setW_uid(String w_uid) {
        this.w_uid = w_uid == null ? null : w_uid.trim();
    }

    public Integer getG_state() {
        return g_state;
    }

    public void setG_state(Integer g_state) {
        this.g_state = g_state;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1 == null ? null : filed1.trim();
    }

    public Integer getFiled2() {
        return filed2;
    }

    public void setFiled2(Integer filed2) {
        this.filed2 = filed2;
    }
}