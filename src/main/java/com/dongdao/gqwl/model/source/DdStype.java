package com.dongdao.gqwl.model.source;

import com.dongdao.gqwl.model.BaseModel;

public class DdStype extends BaseModel {
    private Long s_typeid;

    private String s_name;

    private String s_r_time;

    private String s_m_time;

    private String w_uid;

    private Integer s_state;

    private String filed1;

    private Integer filed2;

    public Long getS_typeid() {
        return s_typeid;
    }

    public void setS_typeid(Long s_typeid) {
        this.s_typeid = s_typeid;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name == null ? null : s_name.trim();
    }

    public String getS_r_time() {
        return s_r_time;
    }

    public void setS_r_time(String s_r_time) {
        this.s_r_time = s_r_time == null ? null : s_r_time.trim();
    }

    public String getS_m_time() {
        return s_m_time;
    }

    public void setS_m_time(String s_m_time) {
        this.s_m_time = s_m_time == null ? null : s_m_time.trim();
    }

    public String getW_uid() {
        return w_uid;
    }

    public void setW_uid(String w_uid) {
        this.w_uid = w_uid == null ? null : w_uid.trim();
    }

    public Integer getS_state() {
        return s_state;
    }

    public void setS_state(Integer s_state) {
        this.s_state = s_state;
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