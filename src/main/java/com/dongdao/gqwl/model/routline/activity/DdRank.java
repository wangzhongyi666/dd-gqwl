package com.dongdao.gqwl.model.routline.activity;

import com.dongdao.gqwl.model.BaseModel;

public class DdRank extends BaseModel {
    private Long randid;

    private Long r_uid;

    private Long actid;

    private Integer integar;

    private String creattime;

    private Integer filed1;

    private String filed2;

    private String name;

    public Long getRandid() {
        return randid;
    }

    public void setRandid(Long randid) {
        this.randid = randid;
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

    public Integer getFiled1() {
        return filed1;
    }

    public void setFiled1(Integer filed1) {
        this.filed1 = filed1;
    }

    public String getFiled2() {
        return filed2;
    }

    public void setFiled2(String filed2) {
        this.filed2 = filed2 == null ? null : filed2.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}