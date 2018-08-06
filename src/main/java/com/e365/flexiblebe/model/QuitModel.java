package com.e365.flexiblebe.model;

public class QuitModel  extends BaseModel {
    private Integer id;
    private String orderNum;
    private Integer uid;
    private Integer insuranceType;
    private Double quit_payment;
    private Integer quit_integration;
    private Integer integration_diff;
    private Double pra_payment;
    private String subTime;
    private Integer audit;
    private String name;
    private String subTime1;
    private String subTime2;
    private Double payment_inte;
    private Integer quitType;
    private Integer monthNum;
    private String quitMonth;
    private String extendMonth;

    public String getExtendMonth() {
        return extendMonth;
    }

    public void setExtendMonth(String extendMonth) {
        this.extendMonth = extendMonth;
    }

    public String getQuitMonth() {
        return quitMonth;
    }

    public void setQuitMonth(String quitMonth) {
        this.quitMonth = quitMonth;
    }

    public Integer getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(Integer monthNum) {
        this.monthNum = monthNum;
    }

    public Integer getQuitType() {
        return quitType;
    }

    public void setQuitType(Integer quitType) {
        this.quitType = quitType;
    }

    public Double getPayment_inte() {
        return payment_inte;
    }

    public void setPayment_inte(Double payment_inte) {
        this.payment_inte = payment_inte;
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

    public Double getQuit_payment() {
        return quit_payment;
    }

    public void setQuit_payment(Double quit_payment) {
        this.quit_payment = quit_payment;
    }

    public Integer getQuit_integration() {
        return quit_integration;
    }

    public void setQuit_integration(Integer quit_integration) {
        this.quit_integration = quit_integration;
    }

    public Integer getIntegration_diff() {
        return integration_diff;
    }

    public void setIntegration_diff(Integer integration_diff) {
        this.integration_diff = integration_diff;
    }

    public Double getPra_payment() {
        return pra_payment;
    }

    public void setPra_payment(Double pra_payment) {
        this.pra_payment = pra_payment;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
