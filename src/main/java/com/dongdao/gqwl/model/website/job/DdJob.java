package com.dongdao.gqwl.model.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdJob extends BaseModel {
    private Long jobid;

    private String jobname;

    private String jobsub;

    private Long jobtypeid;

    private String jarea;

    private String jcontent;

    private String jrequire;

    private String jsalary;

    private Integer j_sort;

    private String j_audit;

    private String creattime;

    private String updatetime;

    private Integer ispass;

    private Integer isdelete;

    private String filed1;

    private Integer filed2;

    private String jobtypename;

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
        this.jobid = jobid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
    }

    public String getJobsub() {
        return jobsub;
    }

    public void setJobsub(String jobsub) {
        this.jobsub = jobsub == null ? null : jobsub.trim();
    }

    public Long getJobtypeid() {
        return jobtypeid;
    }

    public void setJobtypeid(Long jobtypeid) {
        this.jobtypeid = jobtypeid;
    }

    public String getJarea() {
        return jarea;
    }

    public void setJarea(String jarea) {
        this.jarea = jarea == null ? null : jarea.trim();
    }

    public String getJcontent() {
        return jcontent;
    }

    public void setJcontent(String jcontent) {
        this.jcontent = jcontent == null ? null : jcontent.trim();
    }

    public String getJrequire() {
        return jrequire;
    }

    public void setJrequire(String jrequire) {
        this.jrequire = jrequire == null ? null : jrequire.trim();
    }

    public String getJsalary() {
        return jsalary;
    }

    public void setJsalary(String jsalary) {
        this.jsalary = jsalary == null ? null : jsalary.trim();
    }

    public Integer getJ_sort() {
        return j_sort;
    }

    public void setJ_sort(Integer j_sort) {
        this.j_sort = j_sort;
    }

    public String getJ_audit() {
        return j_audit;
    }

    public void setJ_audit(String j_audit) {
        this.j_audit = j_audit == null ? null : j_audit.trim();
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

    public Integer getIspass() {
        return ispass;
    }

    public void setIspass(Integer ispass) {
        this.ispass = ispass;
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

    public String getJobtypename() {
        return jobtypename;
    }

    public void setJobtypename(String jobtypename) {
        this.jobtypename = jobtypename;
    }
}