package com.dongdao.gqwl.bean;

import java.util.Date;

public class GoodsOrder extends BaseBean {

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
    private String write_off_state_zh;//核销状态 1 ，未发货；2,已发货；（发货状态
    private String deliver_goods_address;//发货地址
    private String vip_id;//
    private String vname;//用户名称
    private String deliver_goods_tel;//手机号
    private String onsignee_name;//收货人姓名
    private Date create_time;//插入时间
    private String write_off_info;
    private String stateCh_zn;
    private String term_of_validity_start;
    private String term_of_validity_end;
    private Integer take_type;
    private String imgUrl;
    private String info;
    private String statement;
    private String term_of_validity_time;
    private String redeem_code;
    private String[] imgUrls;
    private String status;
    private String tel;
    private String waybill_number;
    private String courier_firm;
    private Double payment;
    private Integer is_pay;
    private String exchange_time_str;

    public String getExchange_time_str() {
        return exchange_time_str;
    }

    public void setExchange_time_str(String exchange_time_str) {
        this.exchange_time_str = exchange_time_str;
    }

    public Integer getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(Integer is_pay) {
        this.is_pay = is_pay;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String[] imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getRedeem_code() {
        return redeem_code;
    }

    public void setRedeem_code(String redeem_code) {
        this.redeem_code = redeem_code;
    }

    public String getWrite_off_state_zh() {
        return write_off_state_zh;
    }

    public void setWrite_off_state_zh(String write_off_state_zh) {
        this.write_off_state_zh = write_off_state_zh;
    }

    public String getStatement() {

        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getTerm_of_validity_time() {
        return term_of_validity_time;
    }

    public void setTerm_of_validity_time(String term_of_validity_time) {
        this.term_of_validity_time = term_of_validity_time;
    }

    public String getTerm_of_validity_start() {
        return term_of_validity_start;
    }

    public void setTerm_of_validity_start(String term_of_validity_start) {
        this.term_of_validity_start = term_of_validity_start;
    }

    public String getTerm_of_validity_end() {
        return term_of_validity_end;
    }

    public void setTerm_of_validity_end(String term_of_validity_end) {
        this.term_of_validity_end = term_of_validity_end;
    }

    public Integer getTake_type() {
        return take_type;
    }

    public void setTake_type(Integer take_type) {
        this.take_type = take_type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStateCh_zn() {
        return stateCh_zn;
    }

    public void setStateCh_zn(String stateCh_zn) {
        this.stateCh_zn = stateCh_zn;
    }

    public String getWrite_off_info() {
        return write_off_info;
    }

    public void setWrite_off_info(String write_off_info) {
        this.write_off_info = write_off_info;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
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

    public String getVip_id() {
        return vip_id;
    }

    public void setVip_id(String vip_id) {
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
