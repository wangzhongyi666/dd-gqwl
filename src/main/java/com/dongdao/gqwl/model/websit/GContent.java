package com.dongdao.gqwl.model.websit;

import com.dongdao.gqwl.model.BaseModel;

public class GContent extends BaseModel {
    private Integer id;

    private Integer g_tid;

    private Integer s_sid;

    private Integer g_pv;

    private Float g_rank;

    private Integer g_recommend;

    private Integer g_top;

    private String g_R_time;

    private String g_M_time;

    private Integer w_uid;

    private Integer g_state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getG_tid() {
        return g_tid;
    }

    public void setG_tid(Integer g_tid) {
        this.g_tid = g_tid;
    }

    public Integer getS_sid() {
        return s_sid;
    }

    public void setS_sid(Integer s_sid) {
        this.s_sid = s_sid;
    }

    public Integer getG_pv() {
        return g_pv;
    }

    public void setG_pv(Integer g_pv) {
        this.g_pv = g_pv;
    }

    public Float getG_rank() {
        return g_rank;
    }

    public void setG_rank(Float g_rank) {
        this.g_rank = g_rank;
    }

    public Integer getG_recommend() {
        return g_recommend;
    }

    public void setG_recommend(Integer g_recommend) {
        this.g_recommend = g_recommend;
    }

    public Integer getG_top() {
        return g_top;
    }

    public void setG_top(Integer g_top) {
        this.g_top = g_top;
    }

    public String getG_R_time() {
        return g_R_time;
    }

    public void setG_R_time(String g_R_time) {
        this.g_R_time = g_R_time == null ? null : g_R_time.trim();
    }

    public String getG_M_time() {
        return g_M_time;
    }

    public void setG_M_time(String g_M_time) {
        this.g_M_time = g_M_time == null ? null : g_M_time.trim();
    }

    public Integer getW_uid() {
        return w_uid;
    }

    public void setW_uid(Integer w_uid) {
        this.w_uid = w_uid;
    }

    public Integer getG_state() {
        return g_state;
    }

    public void setG_state(Integer g_state) {
        this.g_state = g_state;
    }
}