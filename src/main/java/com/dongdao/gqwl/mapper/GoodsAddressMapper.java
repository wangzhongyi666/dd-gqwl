package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.GoodsAddress;
import com.dongdao.gqwl.model.GoodsAddressModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsAddressMapper<T> extends BaseMapper {

    @Select("<script>SELECT t.*,v.name,v.tel FROM goods_address t left join sys_vip v on t.vip_id = v.id WHERE 1=1 " +
            "<if test=\"id != null and id != 0\"> and t.id = #{id} </if> " +
            "<if test=\"vip_id != null and vip_id != 0\"> and t.vip_id = #{vip_id} </if>" +
            "<if test=\"is_default != null and is_default != 0\"> and is_default = #{is_default} </if>" +
            "</script>")
    List<GoodsAddress> queryByList(GoodsAddressModel model);
    @Select("<script>SELECT count(1) FROM goods_address t left join sys_vip v on t.vip_id = v.id WHERE 1=1 " +
            "<if test=\"vip_id != null and vip_id != 0\"> and t.vip_id = #{vip_id} </if>" +
            "<if test=\"is_default != null and is_default != 0\"> and is_default = #{is_default} </if>" +
            "</script>")
    Integer countAddresslist(GoodsAddressModel model);
    @Select("<script>SELECT t.id,t.vip_id,t.person_name,t.person_tel,t.province,t.province_zh,t.city,t.city_zh,t.area,t.area_zh,t.address," +
            "CASE t.is_default WHEN 1 THEN 1 ELSE 2 END as is_default " +
            "FROM goods_address t left join sys_vip v on t.vip_id = v.id WHERE 1=1 " +
            "<if test=\"vip_id != null and vip_id != 0\"> and t.vip_id = #{vip_id} </if>" +
            " order by is_default" +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>"+
            "</script>")
    List<Map<String,Object>> getAddresslist(GoodsAddressModel model);
    @Update("<script>update goods_address set vip_id=vip_id " +
            "<if test=\"person_name != null and person_name != ''\"> ,person_name = #{person_name} </if>" +
            "<if test=\"person_tel != null and person_tel !=''\"> ,person_tel = #{person_tel} </if>" +
            "<if test=\"province_zh != null and province_zh != ''\"> ,province_zh = #{province_zh} </if>" +
            "<if test=\"city_zh != null and city_zh != ''\"> ,city_zh = #{city_zh} </if>" +
            "<if test=\"area_zh != null and area_zh != ''\"> ,area_zh = #{area_zh} </if>" +
            "<if test=\"province != null and province != ''\"> ,province = #{province} </if>" +
            "<if test=\"city != null and city != ''\"> ,city = #{city} </if>" +
            "<if test=\"area != null and area != ''\"> ,area = #{area} </if>" +
            "<if test=\"address != null and address != ''\"> ,address = #{address} </if>" +
            "<if test=\"is_default != null and is_default != 0\"> ,is_default = #{is_default} </if>" +
            " where id=#{id}</script>")
    void updateAddress(GoodsAddressModel model);

    @Update("<script>update goods_address set is_default=2 where vip_id=#{vip_id} and is_default=1</script>")
    void updateDefAddressByVipId(GoodsAddressModel model);
    @Update("<script>update goods_address set is_default=1 where id=#{id}</script>")
    void updateDefAddress(GoodsAddressModel model);
    @Delete("<script>delete from goods_address where id=#{id}</script>")
    void deleteAddress(GoodsAddressModel model);
    @Insert("<script>insert into goods_address (vip_id,person_name,person_tel,province,province_zh,city,city_zh,area,area_zh,address,is_default) " +
            " value(#{vip_id},#{person_name},#{person_tel},#{province},#{province_zh},#{city},#{city_zh},#{area},#{area_zh},#{address},#{is_default})</script>")
    void addAddress(GoodsAddressModel model);

    @Select("<script>SELECT t.*,v.name,v.tel FROM goods_address t left join sys_vip v on t.vip_id = v.id WHERE 1=1 " +
            "<if test=\"id != null and id != 0\"> and t.id = #{id} </if> </script>")
    GoodsAddress queryByAddressId(@Param("id") Integer id);
}
