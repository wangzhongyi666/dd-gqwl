package com.e365.flexiblebe.model;

public class OrderInfoModel extends BaseModel {
    private Integer id;
    private String orderNum;
    private Integer uid;
    private Integer insuranceType;
    private Integer insuranceNature;
    private String inseuranceCycle;
    private String insuStart;
    private String insuEnd;
    private Integer monthNum;
    private String subTime;
    private String auditTime;
    private Double base;
    private Integer ratio;
    private Double payment;
    private Double unitPrice;
    private Integer status;
    private Integer audit;
    private Integer audit1;
    private Integer audit2;
    private String name;
    private String subTime1;
    private String subTime2;

    private String insuStart1;
    private String insuEnd1;

    private Integer rank;

    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    private Integer isquit;

    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
    public Integer getIsquit() {
        return isquit;
    }

    public void setIsquit(Integer isquit) {
        this.isquit = isquit;
    }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAudit1() {
        return audit1;
    }

    public void setAudit1(Integer audit1) {
        this.audit1 = audit1;
    }

    public Integer getAudit2() {
        return audit2;
    }

    public void setAudit2(Integer audit2) {
        this.audit2 = audit2;
    }

    public Integer getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(Integer monthNum) {
        this.monthNum = monthNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getInseuranceCycle() {
        return inseuranceCycle;
    }

    public void setInseuranceCycle(String inseuranceCycle) {
        this.inseuranceCycle = inseuranceCycle;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }
}
