package com.dongdao.gqwl.model.websit;

import com.dongdao.gqwl.model.BaseModel;

public class GSeo extends BaseModel {
    private Integer id;

    private Integer g_tid;

    private String g_icon;

    private String g_title;

    private String g_keywords; 

    private String g_description;

    private String g_url;

    private String g_tkey;

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

    public String getG_icon() {
        return g_icon;
    }

    public void setG_icon(String g_icon) {
        this.g_icon = g_icon == null ? null : g_icon.trim();
    }

    public String getG_title() {
        return g_title;
    }

    public void setG_title(String g_title) {
        this.g_title = g_title == null ? null : g_title.trim();
    }

    public String getG_keywords() {
        return g_keywords;
    }

    public void setG_keywords(String g_keywords) {
        this.g_keywords = g_keywords == null ? null : g_keywords.trim();
    }

    public String getG_description() {
        return g_description;
    }

    public void setG_description(String g_description) {
        this.g_description = g_description == null ? null : g_description.trim();
    }

    public String getG_url() {
        return g_url;
    }

    public void setG_url(String g_url) {
        this.g_url = g_url == null ? null : g_url.trim();
    }

    public String getG_tkey() {
        return g_tkey;
    }

    public void setG_tkey(String g_tkey) {
        this.g_tkey = g_tkey == null ? null : g_tkey.trim();
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