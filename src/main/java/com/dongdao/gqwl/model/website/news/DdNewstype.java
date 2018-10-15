package com.dongdao.gqwl.model.website.news;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.BaseModel;

public class DdNewstype extends BaseModel {
    private Long newstypeid;

    private Integer newstype;

    private String newstypename;

    private Integer isdelete;

    private Integer n_sort;

    private String n_audit;

    private String creattime;

    public Long getNewstypeid() {
        return newstypeid;
    }

    public void setNewstypeid(Long newstypeid) {
        this.newstypeid = newstypeid;
    }

    public Integer getNewstype() {
        return newstype;
    }

    public void setNewstype(Integer newstype) {
        this.newstype = newstype;
    }

    public String getNewstypename() {
        return newstypename;
    }

    public void setNewstypename(String newstypename) {
        this.newstypename = newstypename == null ? null : newstypename.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getN_sort() {
        return n_sort;
    }

    public void setN_sort(Integer n_sort) {
        this.n_sort = n_sort;
    }

    public String getN_audit() {
        return n_audit;
    }

    public void setN_audit(String n_audit) {
        this.n_audit = n_audit == null ? null : n_audit.trim();
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }
}