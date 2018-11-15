package com.dongdao.gqwl.model.routline.activity;

import com.dongdao.gqwl.model.BaseModel;

public class DdInget extends BaseModel {
    private Long ingetid;

    private Long r_uid;

    private Long actid;

    private Integer integar;

    private String creattime;

    private String name;

    private String filed1;

    private Integer filed2;

    public Long getIngetid() {
        return ingetid;
    }

    public void setIngetid(Long ingetid) {
        this.ingetid = ingetid;
    }

    public Long getR_uid() {
        return r_uid;
    }

    public void setR_uid(Long r_uid) {
        this.r_uid = r_uid;
    }

    public Long getActid() {
        return actid;
    }

    public void setActid(Long actid) {
        this.actid = actid;
    }

    public Integer getIntegar() {
        return integar;
    }

    public void setIntegar(Integer integar) {
        this.integar = integar;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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