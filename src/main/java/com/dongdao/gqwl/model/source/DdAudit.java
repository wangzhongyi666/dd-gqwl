package com.dongdao.gqwl.model.source;
import com.dongdao.gqwl.model.BaseModel;

public class DdAudit extends BaseModel {
    private Long s_audit_id;

    private Long s_contentid;

    private String smessage;

    private String s_r_time;

    private String w_uid;

    private Integer s_state;

    private String filed1;

    private Integer filed2;

    private String nickName;

    private String s_title;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getS_title() {
        return s_title;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public Long getS_contentid() {
        return s_contentid;
    }

    public void setS_contentid(Long s_contentid) {
        this.s_contentid = s_contentid;
    }

    public String getSmessage() {
        return smessage;
    }

    public Long getS_audit_id() {
        return s_audit_id;
    }

    public void setS_audit_id(Long s_audit_id) {
        this.s_audit_id = s_audit_id;
    }

    public void setSmessage(String smessage) {
        this.smessage = smessage == null ? null : smessage.trim();
    }

    public String getS_r_time() {
        return s_r_time;
    }

    public void setS_r_time(String s_r_time) {
        this.s_r_time = s_r_time == null ? null : s_r_time.trim();
    }

    public String getW_uid() {
        return w_uid;
    }

    public void setW_uid(String w_uid) {
        this.w_uid = w_uid == null ? null : w_uid.trim();
    }

    public Integer getS_state() {
        return s_state;
    }

    public void setS_state(Integer s_state) {
        this.s_state = s_state;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1 == null ? null : filed1.trim();
    }

    public Integer getFiled2() {
        return filed2;
    }

    public void setFiled2(Integer filed2) {
        this.filed2 = filed2;
    }
}