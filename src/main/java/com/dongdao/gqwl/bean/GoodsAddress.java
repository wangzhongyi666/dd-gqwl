package com.dongdao.gqwl.bean;

public class GoodsAddress extends BaseBean {

    private Integer id;
    private Integer vip_id;//用户id
    private String person_name;
    private String person_tel;
    private String address;//收货
    private Integer is_default;//是否默认
    private String name;
    private String tel;
    private Integer province;
    private String province_zh;
    private Integer city;
    private String city_zh;
    private Integer area;
    private String area_zh;


    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_tel() {
        return person_tel;
    }

    public void setPerson_tel(String person_tel) {
        this.person_tel = person_tel;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getProvince_zh() {
        return province_zh;
    }

    public void setProvince_zh(String province_zh) {
        this.province_zh = province_zh;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getCity_zh() {
        return city_zh;
    }

    public void setCity_zh(String city_zh) {
        this.city_zh = city_zh;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getArea_zh() {
        return area_zh;
    }

    public void setArea_zh(String area_zh) {
        this.area_zh = area_zh;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
