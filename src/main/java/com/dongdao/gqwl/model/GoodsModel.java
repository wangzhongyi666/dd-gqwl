package com.dongdao.gqwl.model;

import java.util.Date;

public class GoodsModel extends BaseModel {

    private Integer id;
    private String number;//商品编号
    private String gname;//商品名称
    private Integer integral;//积分
    private Integer stock;//库存
    private Integer take_type;//提货方式 1 到店自提； 2 邮寄；
    private Integer exchange;//兑换方式 1 每个用户id只能兑换一个；2 不限制
    private String imgUrl;//图片地址
    private String info;//商品详情
    private String statement;//重要声明
    private Integer state;// 1 上架；2 下架
    private Date createTime;

    private Integer mail_nums;//邮寄数量
    private Integer exchanged_nums;//已兑换数量
    private Integer writed_off_nums;//已核销数量
    private Integer write_off_nums;//未核销数量
    private Integer not_exchange_nums;//未兑换数量
    private Integer pick_up_nums;//自提数量
    private String startTime;
    private String endTime;

    private String startDate;//逻辑条件
    private String endDate;//逻辑条件
    private Integer rankTime;
    private String term_of_validity_start;

    private String redeem_code;

    public String getRedeem_code() {
        return redeem_code;
    }

    public void setRedeem_code(String redeem_code) {
        this.redeem_code = redeem_code;
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

    private String term_of_validity_end;

    public Integer getRankTime() {
        return rankTime;
    }

    public void setRankTime(Integer rankTime) {
        this.rankTime = rankTime;
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

    public Integer getMail_nums() {
        return mail_nums;
    }

    public void setMail_nums(Integer mail_nums) {
        this.mail_nums = mail_nums;
    }

    public Integer getExchanged_nums() {
        return exchanged_nums;
    }

    public void setExchanged_nums(Integer exchanged_nums) {
        this.exchanged_nums = exchanged_nums;
    }

    public Integer getWrited_off_nums() {
        return writed_off_nums;
    }

    public void setWrited_off_nums(Integer writed_off_nums) {
        this.writed_off_nums = writed_off_nums;
    }

    public Integer getWrite_off_nums() {
        return write_off_nums;
    }

    public void setWrite_off_nums(Integer write_off_nums) {
        this.write_off_nums = write_off_nums;
    }

    public Integer getNot_exchange_nums() {
        return not_exchange_nums;
    }

    public void setNot_exchange_nums(Integer not_exchange_nums) {
        this.not_exchange_nums = not_exchange_nums;
    }

    public Integer getPick_up_nums() {
        return pick_up_nums;
    }

    public void setPick_up_nums(Integer pick_up_nums) {
        this.pick_up_nums = pick_up_nums;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTake_type() {
        return take_type;
    }

    public void setTake_type(Integer take_type) {
        this.take_type = take_type;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
