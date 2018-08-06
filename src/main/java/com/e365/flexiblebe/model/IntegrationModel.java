package com.e365.flexiblebe.model;

public class IntegrationModel extends BaseModel{
    private Integer id;
    private Integer uid;
    private Integer integration;
    private Integer type;
    private String addTime;
    private String updateTime;
    private String tel;
    private Integer quit_id;

    public Integer getQuit_id() {
        return quit_id;
    }

    public void setQuit_id(Integer quit_id) {
        this.quit_id = quit_id;
    }
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
