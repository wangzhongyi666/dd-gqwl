package com.dongdao.gqwl.model.routline.topic;

import com.dongdao.gqwl.model.BaseModel;

public class DdZrecord extends BaseModel {
    private Long zrecordid;

    private Long r_uid;

    private Long cardid;

    private String creattime;

    private Integer status;

    public Long getZrecordid() {
        return zrecordid;
    }

    public void setZrecordid(Long zrecordid) {
        this.zrecordid = zrecordid;
    }

    public Long getR_uid() {
        return r_uid;
    }

    public void setR_uid(Long r_uid) {
        this.r_uid = r_uid;
    }

    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}