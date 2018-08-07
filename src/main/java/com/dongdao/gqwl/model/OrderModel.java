package com.dongdao.gqwl.model;

public class OrderModel extends BaseModel{
    private Integer id;
    private String orderNum;
    private Integer uid;
    private Integer insuranceType;
    private Integer insuranceNature;
    private String insuStart;
    private String insuEnd;
    private String subTime;
    private String rechargeTime;
    private String auditTime;
    private Double base;
    private Integer ratio;
    private Double payment;
    private Integer audit;
    private String rechargeTime1;
    private String rechargeTime2;
    private String name;
    private String tel;//逻辑条件

    private String subStartTime;//逻辑条件
    private String subEndTime;//逻辑条件
    private String insuranceNum;
    private Integer audit1;

    private String subStartTime1;
    private String subEndTime1;
    private String insuStart1;
    private String insuEnd1;
    private Double balance;
    private String remarks;
    private String tname;
    private Integer updateType;
    private String oldStart;
    private String oldEnd;
    private Double oldPayment;
    private Double monthPayment;
    private String subTime1;
    private String subTime2;

    public String getSubTime1() {
        return subTime1;
    }

    public void setSubTime1(String subTime1) {
        this.subTime1 = subTime1;
    }

    public String getSubTime2() {
        return subTime2;
    }

    public void setSubTime2(String subTime2) {
        this.subTime2 = subTime2;
    }

    public Double getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(Double monthPayment) {
        this.monthPayment = monthPayment;
    }

    public Double getOldPayment() {
        return oldPayment;
    }

    public void setOldPayment(Double oldPayment) {
        this.oldPayment = oldPayment;
    }

    public String getOldStart() {
        return oldStart;
    }

    public void setOldStart(String oldStart) {
        this.oldStart = oldStart;
    }

    public String getOldEnd() {
        return oldEnd;
    }

    public void setOldEnd(String oldEnd) {
        this.oldEnd = oldEnd;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getAudit1() {
        return audit1;
    }

    public void setAudit1(Integer audit1) {
        this.audit1 = audit1;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getInsuStart1() {
        return insuStart1;
    }

    public void setInsuStart1(String insuStart1) {
        this.insuStart1 = insuStart1;
    }

    public String getInsuEnd1() {
        return insuEnd1;
    }

    public void setInsuEnd1(String insuEnd1) {
        this.insuEnd1 = insuEnd1;
    }

    public String getSubEndTime1() {
        return subEndTime1;
    }

    public void setSubEndTime1(String subEndTime1) {
        this.subEndTime1 = subEndTime1;
    }

    public String getSubStartTime1() {
        return subStartTime1;
    }

    public void setSubStartTime1(String subStartTime1) {
        this.subStartTime1 = subStartTime1;
    }

    public String getInsuranceNum() {
        return insuranceNum;
    }

    public void setInsuranceNum(String insuranceNum) {
        this.insuranceNum = insuranceNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSubStartTime() {
        return subStartTime;
    }

    public void setSubStartTime(String subStartTime) {
        this.subStartTime = subStartTime;
    }

    public String getSubEndTime() {
        return subEndTime;
    }

    public void setSubEndTime(String subEndTime) {
        this.subEndTime = subEndTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Integer insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Integer getInsuranceNature() {
        return insuranceNature;
    }

    public void setInsuranceNature(Integer insuranceNature) {
        this.insuranceNature = insuranceNature;
    }

    public String getInsuStart() {
        return insuStart;
    }

    public void setInsuStart(String insuStart) {
        this.insuStart = insuStart;
    }

    public String getInsuEnd() {
        return insuEnd;
    }

    public void setInsuEnd(String insuEnd) {
        this.insuEnd = insuEnd;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getRechargeTime1() {
        return rechargeTime1;
    }

    public void setRechargeTime1(String rechargeTime1) {
        this.rechargeTime1 = rechargeTime1;
    }

    public String getRechargeTime2() {
        return rechargeTime2;
    }

    public void setRechargeTime2(String rechargeTime2) {
        this.rechargeTime2 = rechargeTime2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
