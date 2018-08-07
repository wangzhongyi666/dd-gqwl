package com.dongdao.gqwl.model;

import java.util.Date;

public class GoodsOrderModel extends BaseModel {

    private Integer id;
    private Integer goods_id;//商品id
    private String goods_number;//商品编号
    private String goods_name;//商品名称
    private String order_number;//订单号
    private Integer nums;//兑换数量
    private Integer integral;//消耗积分
    private String exchange_number;//兑换账号
    private Date exchange_time;//兑换时间
    private String write_off_time;//核销时间  （发货时间）
    private Integer write_off_state;//核销状态 1 ，未发货；2,已发货；（发货状态）
    private String deliver_goods_address;//发货地址
    private Integer vip_id;//
    private String vname;//用户名称
    private String deliver_goods_tel;//手机号
    private String onsignee_name;//收货人姓名
    private String goods_nn;//逻辑条件
    private String startTime;//逻辑条件
    private String endTime;//逻辑条件
    private String startDate;//逻辑条件
    private String endDate;//逻辑条件
    private Date create_time;
    private String startDate1;//逻辑条件
    private String endDate1;//逻辑条件
    private Integer write_off_state1;
    private Integer write_off_state2;
    private Integer take_type;

    private Integer address_id;
    private Integer status;
    private String waybill_number;
    private String courier_firm;
    private String redeem_code;
    private String startDate2;//逻辑条件
    private String endDate2;//逻辑条件

    public String getStartDate2() {
        return startDate2;
    }

    public void setStartDate2(String startDate2) {
        this.startDate2 = startDate2;
    }

    public String getEndDate2() {
        return endDate2;
    }

    public void setEndDate2(String endDate2) {
        this.endDate2 = endDate2;
    }

    public String getRedeem_code() {
        return redeem_code;
    }

    public void setRedeem_code(String redeem_code) {
        this.redeem_code = redeem_code;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getCourier_firm() {
        return courier_firm;
    }

    public void setCourier_firm(String courier_firm) {
        this.courier_firm = courier_firm;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Integer getTake_type() {
        return take_type;
    }

    public void setTake_type(Integer take_type) {
        this.take_type = take_type;
    }

    public Integer getWrite_off_state1() {
        return write_off_state1;
    }

    public void setWrite_off_state1(Integer write_off_state1) {
        this.write_off_state1 = write_off_state1;
    }

    public Integer getWrite_off_state2() {
        return write_off_state2;
    }

    public void setWrite_off_state2(Integer write_off_state2) {
        this.write_off_state2 = write_off_state2;
    }

    public String getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getGoods_nn() {
        return goods_nn;
    }

    public void setGoods_nn(String goods_nn) {
        this.goods_nn = goods_nn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getExchange_number() {
        return exchange_number;
    }

    public void setExchange_number(String exchange_number) {
        this.exchange_number = exchange_number;
    }

    public Date getExchange_time() {
        return exchange_time;
    }

    public void setExchange_time(Date exchange_time) {
        this.exchange_time = exchange_time;
    }

    public String getWrite_off_time() {
        return write_off_time;
    }

    public void setWrite_off_time(String write_off_time) {
        this.write_off_time = write_off_time;
    }

    public Integer getWrite_off_state() {
        return write_off_state;
    }

    public void setWrite_off_state(Integer write_off_state) {
        this.write_off_state = write_off_state;
    }

    public String getDeliver_goods_address() {
        return deliver_goods_address;
    }

    public void setDeliver_goods_address(String deliver_goods_address) {
        this.deliver_goods_address = deliver_goods_address;
    }

    public Integer getVip_id() {
        return vip_id;
    }

    public void setVip_id(Integer vip_id) {
        this.vip_id = vip_id;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getDeliver_goods_tel() {
        return deliver_goods_tel;
    }

    public void setDeliver_goods_tel(String deliver_goods_tel) {
        this.deliver_goods_tel = deliver_goods_tel;
    }

    public String getOnsignee_name() {
        return onsignee_name;
    }

    public void setOnsignee_name(String onsignee_name) {
        this.onsignee_name = onsignee_name;
    }
}
