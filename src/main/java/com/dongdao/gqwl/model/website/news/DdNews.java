package com.dongdao.gqwl.model.website.news;

import com.dongdao.gqwl.model.BaseModel;

import java.util.Date;

public class DdNews extends BaseModel {
    private Long newsid;

    private String n_audit;

    private String newstitle;

    private String newssubhead;

    private String newskeyword;

    private Integer newstype;

    private String newspic;

    private String newscreattime;

    private String newshtml;

    private String updatetime;

    private Integer newswhere;

    private String creattime;

    private Long hits;

    private Integer top;

    private Integer isdelete;

    private String newsfrom;

    private Integer n_sort;

    private String newsdigest;

    private String newsbody;

    private String newstypename;

    public Long getNewsid() {
        return newsid;
    }

    public void setNewsid(Long newsid) {
        this.newsid = newsid;
    }

    public String getN_audit() {
        return n_audit;
    }

    public void setN_audit(String n_audit) {
        this.n_audit = n_audit == null ? null : n_audit.trim();
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle == null ? null : newstitle.trim();
    }

    public String getNewssubhead() {
        return newssubhead;
    }

    public void setNewssubhead(String newssubhead) {
        this.newssubhead = newssubhead == null ? null : newssubhead.trim();
    }

    public String getNewskeyword() {
        return newskeyword;
    }

    public void setNewskeyword(String newskeyword) {
        this.newskeyword = newskeyword == null ? null : newskeyword.trim();
    }

    public Integer getNewstype() {
        return newstype;
    }

    public void setNewstype(Integer newstype) {
        this.newstype = newstype;
    }

    public String getNewspic() {
        return newspic;
    }

    public void setNewspic(String newspic) {
        this.newspic = newspic == null ? null : newspic.trim();
    }

    public String getNewscreattime() {
        return newscreattime;
    }

    public void setNewscreattime(String newscreattime) {
        this.newscreattime = newscreattime;
    }

    public String getNewshtml() {
        return newshtml;
    }

    public void setNewshtml(String newshtml) {
        this.newshtml = newshtml == null ? null : newshtml.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getNewswhere() {
        return newswhere;
    }

    public void setNewswhere(Integer newswhere) {
        this.newswhere = newswhere;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getNewsfrom() {
        return newsfrom;
    }

    public void setNewsfrom(String newsfrom) {
        this.newsfrom = newsfrom == null ? null : newsfrom.trim();
    }

    public Integer getN_sort() {
        return n_sort;
    }

    public void setN_sort(Integer n_sort) {
        this.n_sort = n_sort;
    }

    public String getNewsdigest() {
        return newsdigest;
    }

    public void setNewsdigest(String newsdigest) {
        this.newsdigest = newsdigest == null ? null : newsdigest.trim();
    }

    public String getNewsbody() {
        return newsbody;
    }

    public void setNewsbody(String newsbody) {
        this.newsbody = newsbody == null ? null : newsbody.trim();
    }

    public String getNewstypename() {
        return newstypename;
    }

    public void setNewstypename(String newstypename) {
        this.newstypename = newstypename;
    }
}