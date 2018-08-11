package com.dongdao.gqwl.model.source;


import com.dongdao.gqwl.model.BaseModel;

public class DdPictype extends BaseModel {



    private Long pictypeid;

    private String typename;

    private Integer tsort;

    private Integer parentid;

    private String filed1;

    public Long getPictypeid() {
        return pictypeid;
    }

    public void setPictypeid(Long pictypeid) {
        this.pictypeid = pictypeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public Integer getTsort() {
        return tsort;
    }

    public void setTsort(Integer tsort) {
        this.tsort = tsort;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1 == null ? null : filed1.trim();
    }
}