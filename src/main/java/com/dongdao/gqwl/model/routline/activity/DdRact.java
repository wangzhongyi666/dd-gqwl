package com.dongdao.gqwl.model.routline.activity;

import com.dongdao.gqwl.model.BaseModel;

public class DdRact extends BaseModel {
    private Long r_actid;

    private Long r_uid;

    private String creattime;

    private Integer nums;

    private String filed1;

    private Integer filed2;

    public Long getR_actid() {
        return r_actid;
    }

    public void setR_actid(Long r_actid) {
        this.r_actid = r_actid;
    }

    public Long getR_uid() {
        return r_uid;
    }

    public void setR_uid(Long r_uid) {
        this.r_uid = r_uid;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
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