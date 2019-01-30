package com.dongdao.gqwl.model.website;

import com.dongdao.gqwl.mapper.BaseMapper;
import com.dongdao.gqwl.model.BaseModel;

public class DdSuggestions extends BaseModel {
    private Integer suggestion_id;

    private Integer user_id;

    private String suggestion;

    private String createtime;

    private String wx_ident;

    private String name;

    private String tel;

    private int types;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWx_ident() {
        return wx_ident;
    }

    public void setWx_ident(String wx_ident) {
        this.wx_ident = wx_ident;
    }

    public Integer getSuggestion_id() {
        return suggestion_id;
    }

    public void setSuggestion_id(Integer suggestion_id) {
        this.suggestion_id = suggestion_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion == null ? null : suggestion.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }
}