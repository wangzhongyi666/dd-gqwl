package com.dongdao.gqwl.model.routline.activity;

import com.dongdao.gqwl.model.BaseModel;

public class DdActivity extends BaseModel {
    private Long actid;

    private String picpath;

    private String begintime;

    private String endtime;

    private String rule;

    private String award;

    private String adescript;

    private Integer maxinter;

    private Integer allinter;

    private Integer ispass;

    private Integer isdelete;

    private String a_audit;

    private String creattime;

    private String updatetime;

    private String filed1;

    private Integer filed2;

    public Long getActid() {
        return actid;
    }

    public void setActid(Long actid) {
        this.actid = actid;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime == null ? null : begintime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award == null ? null : award.trim();
    }

    public String getAdescript() {
        return adescript;
    }

    public void setAdescript(String adescript) {
        this.adescript = adescript == null ? null : adescript.trim();
    }

    public Integer getMaxinter() {
        return maxinter;
    }

    public void setMaxinter(Integer maxinter) {
        this.maxinter = maxinter;
    }

    public Integer getAllinter() {
        return allinter;
    }

    public void setAllinter(Integer allinter) {
        this.allinter = allinter;
    }

    public Integer getIspass() {
        return ispass;
    }

    public void setIspass(Integer ispass) {
        this.ispass = ispass;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getA_audit() {
        return a_audit;
    }

    public void setA_audit(String a_audit) {
        this.a_audit = a_audit == null ? null : a_audit.trim();
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