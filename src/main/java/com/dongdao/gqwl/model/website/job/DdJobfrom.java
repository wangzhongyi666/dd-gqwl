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

    private Integer j_sort;

    private String filed1;

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

    public Integer getJ_sort() {
        return j_sort;
    }

    public void setJ_sort(Integer j_sort) {
        this.j_sort = j_sort;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1 == null ? null : filed1.trim();
    }
}