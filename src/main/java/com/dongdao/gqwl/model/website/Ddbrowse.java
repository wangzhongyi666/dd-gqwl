package com.dongdao.gqwl.model.website;

import com.dongdao.gqwl.model.BaseModel;

public class Ddbrowse extends BaseModel {
    private Integer browse_id;

    private Integer user_id;

    private Integer topid;

    private String createtime;

    private String updatetime;

    private Integer rank;

    private Integer state;

    public Integer getBrowse_id() {
        return browse_id;
    }

    public void setBrowse_id(Integer browse_id) {
        this.browse_id = browse_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTopid() {
        return topid;
    }

    public void setTopid(Integer topid) {
        this.topid = topid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}