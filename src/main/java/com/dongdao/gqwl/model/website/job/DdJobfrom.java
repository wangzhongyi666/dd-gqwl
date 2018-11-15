package com.dongdao.gqwl.model.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdJobfrom extends BaseModel {
    public DdJobfrom() {
    }

    private Long jobfromid;

    private Long jobid;

    private String title;

    private String j_link;

    private String logo;

    private String g_logo;

    private Integer j_sort;

    private String creattime;

    private String j_audit;

    public Long getJobfromid() {
        return jobfromid;
    }

    public void setJobfromid(Long jobfromid) {
        this.jobfromid = jobfromid;
    }

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
        this.jobid = jobid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getJ_link() {
        return j_link;
    }

    public void setJ_link(String j_link) {
        this.j_link = j_link == null ? null : j_link.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getG_logo() {
        return g_logo;
    }

    public void setG_logo(String g_logo) {
        this.g_logo = g_logo;
    }

    public Integer getJ_sort() {
        return j_sort;
    }

    public void setJ_sort(Integer j_sort) {
        this.j_sort = j_sort;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getJ_audit() {
        return j_audit;
    }

    public void setJ_audit(String j_audit) {
        this.j_audit = j_audit;
    }
}