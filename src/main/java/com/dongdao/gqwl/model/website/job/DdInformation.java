package com.dongdao.gqwl.model.website.job;

import com.dongdao.gqwl.model.BaseModel;

public class DdInformation extends BaseModel {
    private Integer information_id;

    private Integer info_type;

    private String title;

    private String content;

    private Integer sendee;

    private String updatetime;

    private Integer rank;

    private Integer is_see;

    private String name;

    private String tel;

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

    public Integer getInformation_id() {
        return information_id;
    }

    public void setInformation_id(Integer information_id) {
        this.information_id = information_id;
    }

    public Integer getInfo_type() {
        return info_type;
    }

    public void setInfo_type(Integer info_type) {
        this.info_type = info_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getSendee() {
        return sendee;
    }

    public void setSendee(Integer sendee) {
        this.sendee = sendee;
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

    public Integer getIs_see() {
        return is_see;
    }

    public void setIs_see(Integer is_see) {
        this.is_see = is_see;
    }
}