package com.e365.flexiblebe.bean;

import java.util.Date;

public class SysDept extends BaseBean {

    private Integer id;
    private Integer deptId;
    private String name;
    private Integer parentId;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
    private Integer rank;
    private Integer is_date;//逻辑条件
    private String subDeptNames;
    private Integer subCount;
    private Double base;
    private Double yilbase;
    private String bank;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Double getYilbase() {
        return yilbase;
    }

    public void setYilbase(Double yilbase) {
        this.yilbase = yilbase;
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public String getSubDeptNames() {
        return subDeptNames;
    }

    public void setSubDeptNames(String subDeptNames) {
        this.subDeptNames = subDeptNames;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getIs_date() {
        return is_date;
    }

    public void setIs_date(Integer is_date) {
        this.is_date = is_date;
    }
}
