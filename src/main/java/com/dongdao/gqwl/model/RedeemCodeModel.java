package com.dongdao.gqwl.model;

import java.util.Date;

public class RedeemCodeModel extends BaseModel {

    private Integer id;
    private Integer goods_id;//商品Id
    private String redeem_code;//兑换码
    private Integer status;//状态 1，未兑换；2，已兑换；3，已核销 ; 4，已核销；

    private String term_of_validity_start;//有效期开始时间
    private String term_of_validity_end;//...结束时间
    private String exchange_time;//兑换时间
    private String write_off_time;//核销时间
    private Date create_time;
    private String goods_name;
    private String startDate;
    private String endDate;
    private String shop_name;
    private String status_zh;

    public String getStatus_zh() {
        return status_zh;
    }

    public void setStatus_zh(String status_zh) {
        this.status_zh = status_zh;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
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

    public String getRedeem_code() {
        return redeem_code;
    }

    public void setRedeem_code(String redeem_code) {
        this.redeem_code = redeem_code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getExchange_time() {
        return exchange_time;
    }

    public void setExchange_time(String exchange_time) {
        this.exchange_time = exchange_time;
    }

    public String getWrite_off_time() {
        return write_off_time;
    }

    public void setWrite_off_time(String write_off_time) {
        this.write_off_time = write_off_time;
    }
}
