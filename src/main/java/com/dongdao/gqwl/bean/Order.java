package com.dongdao.gqwl.bean;

import java.util.Date;

public class Order extends BaseBean {
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
    private String payment;
    private Integer status;
    private Integer audit;
    private String audit1;
    private String name;
    private String tel;
    private String inTypeStr;
    private String identNum;//身份证号
    private String registered;//户口所在地
    private String remarks;
    private String insuranceNum;
    private String insuranceTypeCh_zn;
    private String insuranceNatureCh_zn;
    private String auditCh_zn;
    private String cycle;
    private Date firstTime;
    private String identPicUrl1;
    private String identPicUrl2;
    private String bank;
    private String bankNum;
    private String bankPicUrl;
    private Integer deptId;
    private Integer isquit;
    private Double balance;
    private String deptName;

    private String lastTime;
    private Integer integration;
    private Integer city;
    private Integer province;
    private String poundage;
    private Integer updateType;
    private String oldStart;
    private String oldEnd;
    private Double oldPayment;
    private String medicalUrl;
    private Double monthPayment;
    private String code_url;

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public Double getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(Double monthPayment) {
        this.monthPayment = monthPayment;
    }

    public String getMedicalUrl() {
        return medicalUrl;
    }

    public void setMedicalUrl(String medicalUrl) {
        this.medicalUrl = medicalUrl;
    }

    public Double getOldPayment() {
        return oldPayment;
    }

    public void setOldPayment(Double oldPayment) {
        this.oldPayment = oldPayment;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
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

    public String getAudit1() {
        return audit1;
    }

    public void setAudit1(String audit1) {
        this.audit1 = audit1;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getIsquit() {
        return isquit;
    }

    public void setIsquit(Integer isquit) {
        this.isquit = isquit;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getIdentPicUrl1() {
        return identPicUrl1;
    }

    public void setIdentPicUrl1(String identPicUrl1) {
        this.identPicUrl1 = identPicUrl1;
    }

    public String getIdentPicUrl2() {
        return identPicUrl2;
    }

    public void setIdentPicUrl2(String identPicUrl2) {
        this.identPicUrl2 = identPicUrl2;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getBankPicUrl() {
        return bankPicUrl;
    }

    public void setBankPicUrl(String bankPicUrl) {
        this.bankPicUrl = bankPicUrl;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getInsuranceTypeCh_zn() {
        return insuranceTypeCh_zn;
    }

    public void setInsuranceTypeCh_zn(String insuranceTypeCh_zn) {
        this.insuranceTypeCh_zn = insuranceTypeCh_zn;
    }

    public String getInsuranceNatureCh_zn() {
        return insuranceNatureCh_zn;
    }

    public void setInsuranceNatureCh_zn(String insuranceNatureCh_zn) {
        this.insuranceNatureCh_zn = insuranceNatureCh_zn;
    }

    public String getAuditCh_zn() {
        return auditCh_zn;
    }

    public void setAuditCh_zn(String auditCh_zn) {
        this.auditCh_zn = auditCh_zn;
    }

    public String getInsuranceNum() {
        return insuranceNum;
    }

    public void setInsuranceNum(String insuranceNum) {
        this.insuranceNum = insuranceNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getIdentNum() {
        return identNum;
    }

    public void setIdentNum(String identNum) {
        this.identNum = identNum;
    }

    public String getInTypeStr() {
        return inTypeStr;
    }

    public void setInTypeStr(String inTypeStr) {
        this.inTypeStr = inTypeStr;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
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
