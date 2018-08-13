package com.dongdao.gqwl.model.source;

import java.util.Date;

public class DdVideosource {
    private Long videoid;

    private Long videotypeid;

    private String videotitle;

    private String videohub;

    private Integer vsort;

    private String videopath;

    private String videosize;

    private String picpath;

    private Date creattime;

    private Date updatetime;

    private String pusername;

    private Integer isdelete;

    private String filed1;

    private Integer filed2;

    public Long getVideoid() {
        return videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public Long getVideotypeid() {
        return videotypeid;
    }

    public void setVideotypeid(Long videotypeid) {
        this.videotypeid = videotypeid;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle == null ? null : videotitle.trim();
    }

    public String getVideohub() {
        return videohub;
    }

    public void setVideohub(String videohub) {
        this.videohub = videohub == null ? null : videohub.trim();
    }

    public Integer getVsort() {
        return vsort;
    }

    public void setVsort(Integer vsort) {
        this.vsort = vsort;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath == null ? null : videopath.trim();
    }

    public String getVideosize() {
        return videosize;
    }

    public void setVideosize(String videosize) {
        this.videosize = videosize == null ? null : videosize.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPusername() {
        return pusername;
    }

    public void setPusername(String pusername) {
        this.pusername = pusername == null ? null : pusername.trim();
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
}