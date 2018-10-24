package com.dongdao.gqwl.model.routline.topic;

import com.dongdao.gqwl.model.BaseModel;

public class DdTopic extends BaseModel {
    private Long topid;

    private String title;

    private String picpath;

    private String subtitle;

    private String topupdatetime;

    private String t_content;

    private Integer onlooks;

    private Integer joinnums;

    private String creattime;

    private String updatetime;

    private String t_audit;

    private Integer ispass;

    private Integer isdelete;

    private Integer t_sort;

    public Long getTopid() {
        return topid;
    }

    public void setTopid(Long topid) {
        this.topid = topid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getTopupdatetime() {
        return topupdatetime;
    }

    public void setTopupdatetime(String topupdatetime) {
        this.topupdatetime = topupdatetime == null ? null : topupdatetime.trim();
    }

    public String getT_content() {
        return t_content;
    }

    public void setT_content(String t_content) {
        this.t_content = t_content == null ? null : t_content.trim();
    }

    public Integer getOnlooks() {
        return onlooks;
    }

    public void setOnlooks(Integer onlooks) {
        this.onlooks = onlooks;
    }

    public Integer getJoinnums() {
        return joinnums;
    }

    public void setJoinnums(Integer joinnums) {
        this.joinnums = joinnums;
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

    public String getT_audit() {
        return t_audit;
    }

    public void setT_audit(String t_audit) {
        this.t_audit = t_audit == null ? null : t_audit.trim();
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

    public Integer getT_sort() {
        return t_sort;
    }

    public void setT_sort(Integer t_sort) {
        this.t_sort = t_sort;
    }
}