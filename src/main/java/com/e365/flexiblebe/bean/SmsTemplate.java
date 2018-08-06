package com.e365.flexiblebe.bean;

import java.util.Date;

public class SmsTemplate extends BaseBean{
    private Integer id;
    private String trigger_event;
    private String template;
    private String explain;
    private Integer push_time;
    private Integer status;
    private Integer deptId;
    private Date send_time;

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrigger_event() {
        return trigger_event;
    }

    public void setTrigger_event(String trigger_event) {
        this.trigger_event = trigger_event;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getPush_time() {
        return push_time;
    }

    public void setPush_time(Integer push_time) {
        this.push_time = push_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
