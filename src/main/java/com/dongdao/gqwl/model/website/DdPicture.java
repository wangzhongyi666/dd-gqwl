package com.dongdao.gqwl.model.website;

import com.dongdao.gqwl.model.BaseModel;

public class DdPicture extends BaseModel {
    private Long picid;

    private String title;

    private String pcontent;

    private String subhead;

    private String picpath;

    private Integer p_sort;

    private Integer ptype;

    private Integer ispass;

    private String p_audit;

    private Integer isdelete;

    private String creattime;

    private String updatetime;

    private String filed1;

    private Integer filed2;

    public Long getPicid() {
        return picid;
    }

    public void setPicid(Long picid) {
        this.picid = picid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent == null ? null : pcontent.trim();
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead == null ? null : subhead.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public Integer getP_sort() {
        return p_sort;
    }

    public void setP_sort(Integer p_sort) {
        this.p_sort = p_sort;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getIspass() {
        return ispass;
    }

    public void setIspass(Integer ispass) {
        this.ispass = ispass;
    }

    public String getP_audit() {
        return p_audit;
    }

    public void setP_audit(String p_audit) {
        this.p_audit = p_audit == null ? null : p_audit.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
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