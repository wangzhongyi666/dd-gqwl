package com.dongdao.gqwl.bean.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdJob extends BaseModel {

    private String jobname;

    private String jarea;

    private String jcontent;

    private String jrequire;

    private String jsalary;

    private Integer j_sort;

    private String jobtypename;

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
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

    public String getJobtypename() {
        return jobtypename;
    }

    public void setJobtypename(String jobtypename) {
        this.jobtypename = jobtypename;
    }
}