package com.dongdao.gqwl.model.routline.topic;

import com.dongdao.gqwl.model.BaseModel;

public class DdComment extends BaseModel {
    private Long commentid;

    private Long r_uid;

    private Long to_userid;

    private Long parentid;

    private Long cardid;

    private Integer type;

    private String creattime;

    private Integer ispass;

    private Integer isdelete;

    private String c_content;

    private String name;

    private String picurl;

    private int zannums;

    public Long getCommentid() {
        return commentid;
    }

    public void setCommentid(Long commentid) {
        this.commentid = commentid;
    }

    public Long getR_uid() {
        return r_uid;
    }

    public void setR_uid(Long r_uid) {
        this.r_uid = r_uid;
    }

    public Long getTo_userid() {
        return to_userid;
    }

    public void setTo_userid(Long to_userid) {
        this.to_userid = to_userid;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
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

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content == null ? null : c_content.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getZannums() {
        return zannums;
    }

    public void setZannums(int zannums) {
        this.zannums = zannums;
    }
}