package com.dongdao.gqwl.model.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdJob_from extends BaseModel {
    private Long job_fromid;

    private Long jobid;

    private Long jobfromid;

    private String j_link;

    private Integer filed1;

    private String filed2;

    public Long getJob_fromid() {
        return job_fromid;
    }

    public void setJob_fromid(Long job_fromid) {
        this.job_fromid = job_fromid;
    }

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
        this.jobid = jobid;
    }

    public Long getJobfromid() {
        return jobfromid;
    }

    public void setJobfromid(Long jobfromid) {
        this.jobfromid = jobfromid;
    }

    public String getJ_link() {
        return j_link;
    }

    public void setJ_link(String j_link) {
        this.j_link = j_link == null ? null : j_link.trim();
    }

    public Integer getFiled1() {
        return filed1;
    }

    public void setFiled1(Integer filed1) {
        this.filed1 = filed1;
    }

    public String getFiled2() {
        return filed2;
    }

    public void setFiled2(String filed2) {
        this.filed2 = filed2 == null ? null : filed2.trim();
    }
}