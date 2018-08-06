package com.e365.flexiblebe.bean;

import com.e365.flexiblebe.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Goods extends BaseBean {

    private Integer id;
    private String number;//商品编号
    private String gname;//商品名称
    private Integer integral;//积分
    private Integer stock;//库存
    private Integer take_type;//提货方式 1 到店自提； 2 邮寄；
    private Integer state;// 1 上架；2 下架
    private Integer exchange;//兑换方式 1 每个用户id只能兑换一个；2 不限制
    private String imgUrl;//图片地址
    private String info;//商品详情
    private String statement;//重要声明
    private Date createTime;
    private Integer mail_nums;//邮寄数量
    private Integer exchanged_nums;//已兑换数量
    private Integer writed_off_nums;//已核销数量
    private Integer write_off_nums;//未核销数量
    private Integer not_exchange_nums;//未兑换数量
    private Integer pick_up_nums;//自提数量
    private String term_of_validity_start;//有效期开始时间
    private String term_of_validity_end;//...结束时间
    private Integer type;

    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private String imgUrl4;
    private String[] imgUrls;
    private Integer wdhgq;
    private Integer ydhgq;

    public Integer getWdhgq() {
        return wdhgq;
    }

    public void setWdhgq(Integer wdhgq) {
        this.wdhgq = wdhgq;
    }

    public Integer getYdhgq() {
        return ydhgq;
    }

    public void setYdhgq(Integer ydhgq) {
        this.ydhgq = ydhgq;
    }

    public String[] getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String[] imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {
        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }

    public String getImgUrl4() {
        return imgUrl4;
    }

    public void setImgUrl4(String imgUrl4) {
        this.imgUrl4 = imgUrl4;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public static void main(String[] args)throws  Exception{
        System.out.println(DateUtil.getMonth("2017/03","2018/02"));
    }
}
