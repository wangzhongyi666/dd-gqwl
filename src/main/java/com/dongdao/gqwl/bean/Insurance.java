package com.dongdao.gqwl.bean;

public class Insurance extends BaseBean{
    private Integer id;
    private String insurance;//保险名称
    private Double expends_base;//缴费基数
    private String expends_scale;//缴费比例
    private String expends_amount;//缴费金额
    private String effect_start_time;//养老保险生效开始时间
    private String effect_end_time;//养老保险生效结束时间
    private int state;//养老保险基数生效  1，开启；2,关闭;
    private String[] expends_scales;
    private String[] expends_amounts;
    private Integer deptId;
    private Integer insuranceType;

    private String adjustment_time_start;
    private String adjustment_time_end;

    public String getAdjustment_time_end() {
        return adjustment_time_end;
    }

    public void setAdjustment_time_end(String adjustment_time_end) {
        this.adjustment_time_end = adjustment_time_end;
    }

    public String getAdjustment_time_start() {
        return adjustment_time_start;
    }

    public void setAdjustment_time_start(String adjustment_time_start) {
        this.adjustment_time_start = adjustment_time_start;
    }

    public Integer getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Integer insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String[] getExpends_scales() {
        return expends_scales;
    }

    public void setExpends_scales(String[] expends_scales) {
        this.expends_scales = expends_scales;
    }

    public String[] getExpends_amounts() {
        return expends_amounts;
    }

    public void setExpends_amounts(String[] expends_amounts) {
        this.expends_amounts = expends_amounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public Double getExpends_base() {
        return expends_base;
    }

    public void setExpends_base(Double expends_base) {
        this.expends_base = expends_base;
    }

    public String getExpends_scale() {
        return expends_scale;
    }

    public void setExpends_scale(String expends_scale) {
        this.expends_scale = expends_scale;
    }

    public String getExpends_amount() {
        return expends_amount;
    }

    public void setExpends_amount(String expends_amount) {
        this.expends_amount = expends_amount;
    }

    public String getEffect_start_time() {
        return effect_start_time;
    }

    public void setEffect_start_time(String effect_start_time) {
        this.effect_start_time = effect_start_time;
    }

    public String getEffect_end_time() {
        return effect_end_time;
    }

    public void setEffect_end_time(String effect_end_time) {
        this.effect_end_time = effect_end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
