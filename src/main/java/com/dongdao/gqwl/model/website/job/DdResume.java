package com.dongdao.gqwl.model.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdResume extends BaseModel {
    private Long resumeid;

    private Long r_uid;

    private Long jobid;

    private String filepath;

    private String creattime;

    private String reip;

    private Integer ishandle;

    private Integer isdelete;

    private String filed1;

    private Integer filed2;

    private String jobname;

    private String name;

    public Long getResumeid() {
        return resumeid;
    }

    public void setResumeid(Long resumeid) {
        this.resumeid = resumeid;
    }

    public Long getR_uid() {
        return r_uid;
    }

    public void setR_uid(Long r_uid) {
        this.r_uid = r_uid;
    }

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
        this.jobid = jobid;
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

    public String getReip() {
        return reip;
    }

    public void setReip(String reip) {
        this.reip = reip == null ? null : reip.trim();
    }

    public Integer getIshandle() {
        return ishandle;
    }

    public void setIshandle(Integer ishandle) {
        this.ishandle = ishandle;
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

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}