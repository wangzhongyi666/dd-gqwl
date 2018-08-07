package com.dongdao.gqwl.model;

public class WebinfoModel extends BaseModel {
    private Integer id;
    private String title;
    private String cont;
    private String picurl1;
    private String picurl2;
    private String picurl3;
    private String releTime;
    private Integer releid;
    private Integer deptId;

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicurl1() {
        return picurl1;
    }

    public void setPicurl1(String picurl1) {
        this.picurl1 = picurl1;
    }

    public String getPicurl2() {
        return picurl2;
    }

    public void setPicurl2(String picurl2) {
        this.picurl2 = picurl2;
    }

    public String getPicurl3() {
        return picurl3;
    }

    public void setPicurl3(String picurl3) {
        this.picurl3 = picurl3;
    }

    public String getReleTime() {
        return releTime;
    }

    public void setReleTime(String releTime) {
        this.releTime = releTime;
    }

    public Integer getReleid() {
        return releid;
    }

    public void setReleid(Integer releid) {
        this.releid = releid;
    }
}
