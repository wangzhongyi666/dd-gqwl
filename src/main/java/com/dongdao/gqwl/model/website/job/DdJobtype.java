package com.dongdao.gqwl.model.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdJobtype extends BaseModel {
    private Long jobtypeid;

    private String jobtypename;

    private String j_audit;

    private String creattime;

    private String updatetime;

    public Long getJobtypeid() {
        return jobtypeid;
    }

    public void setJobtypeid(Long jobtypeid) {
        this.jobtypeid = jobtypeid;
    }

    public String getJobtypename() {
        return jobtypename;
    }

    public void setJobtypename(String jobtypename) {
        this.jobtypename = jobtypename == null ? null : jobtypename.trim();
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
}