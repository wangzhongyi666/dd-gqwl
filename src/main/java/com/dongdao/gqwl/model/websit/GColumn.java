package com.dongdao.gqwl.model.websit;

import com.dongdao.gqwl.model.BaseModel;

public class GColumn extends BaseModel {
    private Integer id;

    private String g_name;

    private Integer g_fid;

    private String g_R_time;

    private String g_M_time;

    private Integer w_uid;

    private String g_icon;

    private Boolean g_state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name == null ? null : g_name.trim();
    }

    public Integer getG_fid() {
        return g_fid;
    }

    public void setG_fid(Integer g_fid) {
        this.g_fid = g_fid;
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

    public String getG_icon() {
        return g_icon;
    }

    public void setG_icon(String g_icon) {
        this.g_icon = g_icon == null ? null : g_icon.trim();
    }

    public Boolean getG_state() {
        return g_state;
    }

    public void setG_state(Boolean g_state) {
        this.g_state = g_state;
    }
}