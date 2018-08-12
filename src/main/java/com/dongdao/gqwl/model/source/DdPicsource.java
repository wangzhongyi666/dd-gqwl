package com.dongdao.gqwl.model.source;

import com.dongdao.gqwl.model.BaseModel;

import java.util.Date;

public class DdPicsource extends BaseModel {
    private Long picid;//自增id

    private Long pictypeid;//分类id

    private Long typename;//分类名称

    private String pictitle;//图片标题

    private String pichub;//关键字

    private Integer psort; //排序

    private String picpath;//图片路径

    private String creattime;//创建时间

    private String updatetime;//修改时间

    private String pusername;//发布人

    private Integer isdelete;//是否上架（1，上架  0，下架）

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

    public Long getTypename() {
        return typename;
    }

    public void setTypename(Long typename) {
        this.typename = typename;
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

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
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