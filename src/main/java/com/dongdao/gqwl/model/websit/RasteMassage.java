package com.dongdao.gqwl.model.websit;

import com.dongdao.gqwl.model.BaseModel;

public class RasteMassage extends BaseModel {

    private Integer massage_id;

    private String logo;

    private String two_bar_codes;

    private String address;

    private String tel;

    private String phone;

    private String email;

    private String e_mail;

    private String record;

    private String itude;

    private String longitude;

    private Integer state;

    private String createtime;

    private String updatetime;

    private String inquire_tel;

    private String community_codes;

    public String getCommunity_codes() {
        return community_codes;
    }

    public void setCommunity_codes(String community_codes) {
        this.community_codes = community_codes;
    }

    public String getInquire_tel() {
        return inquire_tel;
    }

    public void setInquire_tel(String inquire_tel) {
        this.inquire_tel = inquire_tel;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public Integer getMassage_id() {
        return massage_id;
    }

    public void setMassage_id(Integer massage_id) {
        this.massage_id = massage_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getTwo_bar_codes() {
        return two_bar_codes;
    }

    public void setTwo_bar_codes(String two_bar_codes) {
        this.two_bar_codes = two_bar_codes == null ? null : two_bar_codes.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
    }

    public String getItude() {
        return itude;
    }

    public void setItude(String itude) {
        this.itude = itude == null ? null : itude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }
}