package com.dongdao.gqwl.model.source;

import java.util.Date;

public class DdPicsource {
    private Long picid;

    private Long pictypeid;

    private String pictitle;

    private String pichub;

    private Integer psort;

    private String picpath;

    private Date creattime;

    private Date updatetime;

    private String pusername;

    private Integer isdelete;

    private String filed1;

    private Integer filed2;

    public Long getPicid() {
        return picid;
    }

    public void setPicid(Long picid) {
        this.picid = picid;
    }

    public Long getPictypeid() {
        return pictypeid;
    }

    public void setPictypeid(Long pictypeid) {
        this.pictypeid = pictypeid;
    }

    public String getPictitle() {
        return pictitle;
    }

    public void setPictitle(String pictitle) {
        this.pictitle = pictitle == null ? null : pictitle.trim();
    }

    public String getPichub() {
        return pichub;
    }

    public void setPichub(String pichub) {
        this.pichub = pichub == null ? null : pichub.trim();
    }

    public Integer getPsort() {
        return psort;
    }

    public void setPsort(Integer psort) {
        this.psort = psort;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPusername() {
        return pusername;
    }

    public void setPusername(String pusername) {
        this.pusername = pusername == null ? null : pusername.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
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