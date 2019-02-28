package com.dongdao.gqwl.model.website;

import com.dongdao.gqwl.model.BaseModel;

public class DdPartner extends BaseModel {
    private Long partner;

    private Integer ptype;

    private String componyname;

    private String partnername;

    private String phone;

    private String email;

    private String introduction;

    private String filepath;

    private String creattime;

    private Integer isdelete;

    public Long getPartner() {
        return partner;
    }

    public void setPartner(Long partner) {
        this.partner = partner;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public String getComponyname() {
        return componyname;
    }

    public void setComponyname(String componyname) {
        this.componyname = componyname == null ? null : componyname.trim();
    }

    public String getPartnername() {
        return partnername;
    }

    public void setPartnername(String partnername) {
        this.partnername = partnername == null ? null : partnername.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}