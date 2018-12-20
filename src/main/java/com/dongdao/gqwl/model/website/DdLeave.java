package com.dongdao.gqwl.model.website;

import com.dongdao.gqwl.model.BaseModel;

public class DdLeave extends BaseModel {
    private Integer leave_id;

    private String phone;

    private String email;

    private String leave;

    private Integer state;

    private String createtime;

    private String starttime;

    private String endtime;

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getLeave_id() {
        return leave_id;
    }

    public void setLeave_id(Integer leave_id) {
        this.leave_id = leave_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave == null ? null : leave.trim();
    }
}