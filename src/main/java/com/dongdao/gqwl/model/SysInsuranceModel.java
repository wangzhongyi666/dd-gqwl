package com.dongdao.gqwl.model;

public class SysInsuranceModel extends BaseModel {

    private Integer id;
    private Integer vip_id;//会员ID
    private String insuranceNum;//社保号
    private String bank;//开户行
    private String bankNum;//医保卡银行账号
    private String medicalUrl;//医保卡照片
    private Integer deptId;// 对应城市Id
    private Integer tstype;
    private String createTime;
    private String medicalUpdateTime;
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMedicalUpdateTime() {
        return medicalUpdateTime;
    }

    public void setMedicalUpdateTime(String medicalUpdateTime) {
        this.medicalUpdateTime = medicalUpdateTime;
    }

    public Integer getTstype() {
        return tstype;
    }

    public void setTstype(Integer tstype) {
        this.tstype = tstype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVip_id() {
        return vip_id;
    }

    public void setVip_id(Integer vip_id) {
        this.vip_id = vip_id;
    }

    public String getInsuranceNum() {
        return insuranceNum;
    }

    public void setInsuranceNum(String insuranceNum) {
        this.insuranceNum = insuranceNum;
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

    public String getMedicalUrl() {
        return medicalUrl;
    }

    public void setMedicalUrl(String medicalUrl) {
        this.medicalUrl = medicalUrl;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
