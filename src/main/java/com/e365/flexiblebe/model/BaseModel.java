package com.e365.flexiblebe.model;

import com.e365.flexiblebe.utils.Pager;
import org.apache.commons.lang.StringUtils;

public class BaseModel {

    private Integer numCount;

    private Integer pageSize;

    private Integer num1;

    private Integer num2;

    private Integer pageNum;

    private Integer deptId;

    public Integer getNumCount() {
        return numCount;
    }

    public void setNumCount(Integer numCount) {
        this.numCount = numCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
