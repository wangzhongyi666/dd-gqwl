package com.dongdao.gqwl.model.website;

import com.dongdao.gqwl.model.BaseModel;

public class DdProfile extends BaseModel {
    private Long proid;

    private Integer type;

    private String title;

    private String entitle;

    private Integer p_sort;

    private Integer ispass;

    private String p_audit;

    private String creattime;

    private String updatetime;

    private String pcontent;

    private String ptcontent;

    private String filed1;

    private Integer filed2;

    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getEntitle() {
        return entitle;
    }

    public void setEntitle(String entitle) {
        this.entitle = entitle == null ? null : entitle.trim();
    }

    public Integer getP_sort() {
        return p_sort;
    }

    public void setP_sort(Integer p_sort) {
        this.p_sort = p_sort;
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

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent == null ? null : pcontent.trim();
    }

    public String getPtcontent() {
        return ptcontent;
    }

    public void setPtcontent(String ptcontent) {
        this.ptcontent = ptcontent == null ? null : ptcontent.trim();
    }

    public Integer getFiled2() {
        return filed2;
    }

    public void setFiled2(Integer filed2) {
        this.filed2 = filed2;
    }
}