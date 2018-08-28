package com.dongdao.gqwl.bean;

public class BaseBean {
    /**
     * 状态枚举
     * @author lu
     *
     */
    public enum STATE {
        ENABLE(0, "可用"), DISABLE(1,"禁用");
        public int key;
        public String value;
        STATE(int key, String value) {
            this.key = key;
            this.value = value;
        }
        public static STATE get(int key) {
            STATE[] values = STATE.values();
            for (STATE object : values) {
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
    public enum DELETED {
        NO(0, "未删除"), YES(1,"已删除");
        public int key;
        public String value;
        DELETED(int key, String value) {
            this.key = key;
            this.value = value;
        }
        public static DELETED get(int key) {
            DELETED[] values = DELETED.values();
            for (DELETED object : values) {
                if (object.key == key) {
                    return object;
                }
            }
            return null;
        }
    }
}
