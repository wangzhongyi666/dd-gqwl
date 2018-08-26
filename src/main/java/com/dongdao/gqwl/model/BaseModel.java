package com.dongdao.gqwl.model;

import com.dongdao.gqwl.bean.BaseBean;

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

    /**
     * 状态枚举
     * @author lu
     *
     */
    public static enum STATE {
        ENABLE(0, "可用"), DISABLE(1,"禁用");
        public int key;
        public String value;
        private STATE(int key, String value) {
            this.key = key;
            this.value = value;
        }
        public static BaseBean.STATE get(int key) {
            BaseBean.STATE[] values = BaseBean.STATE.values();
            for (BaseBean.STATE object : values) {
                if (object.key == key) {
                    return object;
                }
            }
            return null;
        }
    }
    /**
     * 删除枚举
     * @author lu
     *
     */
    public static enum DELETED {
        NO(0, "未删除"), YES(1,"已删除");
        public int key;
        public String value;
        private DELETED(int key, String value) {
            this.key = key;
            this.value = value;
        }
        public static BaseBean.DELETED get(int key) {
            BaseBean.DELETED[] values = BaseBean.DELETED.values();
            for (BaseBean.DELETED object : values) {
                if (object.key == key) {
                    return object;
                }
            }
            return null;
        }
    }
}
