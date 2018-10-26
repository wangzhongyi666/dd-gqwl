package com.dongdao.gqwl.model.routline.topic;

import com.dongdao.gqwl.model.BaseModel;

public class DdCardcon extends BaseModel {
    private Long cardconid;

    private Long cardid;

    private String filepath;

    private Integer isdelete;

    private String filed1;

    private Integer filed2;

    public Long getCardconid() {
        return cardconid;
    }

    public void setCardconid(Long cardconid) {
        this.cardconid = cardconid;
    }

    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
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