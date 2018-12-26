package com.dongdao.gqwl.model.routline.topic;

import com.dongdao.gqwl.model.BaseModel;

public class DdCards extends BaseModel {
    private Long cardid;

    private Long topid;

    private Long r_uid;

    private String openid;

    private String creattime;

    private Integer commnums;

    private Integer zannums;

    private Integer sharenums;

    private Integer ispass;

    private Integer isdelete;

    private Integer type;

    private String filed1;

    private String filed2;

    private Integer filed3;

    private String c_content;

    private String toptitle;

    private String name;

    private String picurl;

    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public Long getTopid() {
        return topid;
    }

    public void setTopid(Long topid) {
        this.topid = topid;
    }

    public Long getR_uid() {
        return r_uid;
    }

    public void setR_uid(Long r_uid) {
        this.r_uid = r_uid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    public Integer getCommnums() {
        return commnums;
    }

    public void setCommnums(Integer commnums) {
        this.commnums = commnums;
    }

    public Integer getZannums() {
        return zannums;
    }

    public void setZannums(Integer zannums) {
        this.zannums = zannums;
    }

    public Integer getSharenums() {
        return sharenums;
    }

    public void setSharenums(Integer sharenums) {
        this.sharenums = sharenums;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1 == null ? null : filed1.trim();
    }

    public String getFiled2() {
        return filed2;
    }

    public void setFiled2(String filed2) {
        this.filed2 = filed2 == null ? null : filed2.trim();
    }

    public Integer getFiled3() {
        return filed3;
    }

    public void setFiled3(Integer filed3) {
        this.filed3 = filed3;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content == null ? null : c_content.trim();
    }

    public String getToptitle() {
        return toptitle;
    }

    public void setToptitle(String toptitle) {
        this.toptitle = toptitle;
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
}