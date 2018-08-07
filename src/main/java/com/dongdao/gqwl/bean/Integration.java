package com.dongdao.gqwl.bean;

public class Integration extends BaseBean{
    private Integer id;
    private Integer uid;
    private Integer integration;
    private Integer type;
    private String addTime;
    private String updateTime;
    private String type_zh;
    private String integration_zh;
    private Integer numType;
    private Integer quit_id;
    private String tel;
    private String name;

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

    public Integer getQuit_id() {
        return quit_id;
    }

    public void setQuit_id(Integer quit_id) {
        this.quit_id = quit_id;
    }

    public Integer getNumType() {
        return numType;
    }

    public void setNumType(Integer numType) {
        this.numType = numType;
    }

    public String getIntegration_zh() {
        return integration_zh;
    }

    public void setIntegration_zh(String integration_zh) {
        this.integration_zh = integration_zh;
    }

    public String getType_zh() {
        return type_zh;
    }

    public void setType_zh(String type_zh) {
        this.type_zh = type_zh;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
