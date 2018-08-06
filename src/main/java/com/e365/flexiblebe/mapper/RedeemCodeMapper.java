package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.Goods;
import com.e365.flexiblebe.bean.RedeemCode;
import com.e365.flexiblebe.model.GoodsModel;
import com.e365.flexiblebe.model.RedeemCodeModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RedeemCodeMapper<T> extends BaseMapper {

    @Select("<script>SELECT * FROM redeem_code t where redeem_code = #{redeem_code}</script>")
    RedeemCode queryByCode(@Param("redeem_code")String redeem_code);

    @Insert("<script>insert into redeem_code (goods_id,goods_name,redeem_code,shop_name,status,term_of_validity_start,term_of_validity_end,exchange_time,write_off_time,create_time) " +
            " values(#{goods_id},#{goods_name},#{redeem_code},#{shop_name},#{status},#{term_of_validity_start},#{term_of_validity_end},#{exchange_time},#{write_off_time},now())</script>")
    Integer addRedeemCode(RedeemCodeModel model);

    @Select("<script>SELECT COUNT(0) FROM redeem_code t WHERE 1=1 "+
            "<if test=\"goods_name != null and goods_name != ''\"> AND (t.goods_name like CONCAT('%', #{goods_name}, '%') " +
            " or t.redeem_code like CONCAT('%', #{goods_name}, '%'))</if> "+
            "<if test=\"startDate != null and startDate != ''\"> AND t.create_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.create_time </if> "+
            "<if test=\"status != null and status != '' and status != 11\">AND t.status = #{status} </if>" +
            "<if test=\"goods_id != null and goods_id != ''\"> AND t.goods_id = #{goods_id} </if> "+
            "<if test=\"status != null and status == 11\">AND t.status in(2,3) </if></script>")
    Integer queryByCount(RedeemCodeModel model);

    @Select("<script>SELECT * FROM redeem_code t WHERE 1=1 "+
            "<if test=\"goods_name != null and goods_name != ''\"> AND (t.goods_name like CONCAT('%', #{goods_name}, '%') " +
            " or t.redeem_code like CONCAT('%', #{goods_name}, '%'))</if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.create_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.create_time </if> "+
            "<if test=\"status != null and status != '' and status != 11\">AND t.status = #{status} </if>"+
            "<if test=\"status != null and status == 11\">AND t.status in(2,3) </if>"+
            "<if test=\"goods_id != null and goods_id != ''\"> AND t.goods_id = #{goods_id} </if> "+
            " order by t.create_time desc" +
            "<if test=\"num1 != null and num2 != null \">limit #{num1},#{num2} </if>"+
            "</script>")
    List<RedeemCode> queryByList(RedeemCodeModel model);

    @Update("<script>update redeem_code set id = id " +
            "<if test=\"shop_name != null and shop_name != ''\">,shop_name = #{shop_name}</if>"+
            "<if test=\"status != null and status != ''\">,status = #{status}</if>"+
            "<if test=\"write_off_time != null and write_off_time != ''\">,write_off_time = #{write_off_time}</if>"+
            "<if test=\"exchange_time != null and exchange_time != ''\">,exchange_time = #{exchange_time}</if>"+
            "<if test=\"status_zh != null and status_zh != ''\">,status_zh = #{status_zh}</if>"+
            "<if test=\"term_of_validity_start != null and term_of_validity_start != ''\">,term_of_validity_start = #{term_of_validity_start}</if>"+
            "<if test=\"term_of_validity_end != null and term_of_validity_end != ''\">,term_of_validity_end = #{term_of_validity_end}</if>"+
            " where redeem_code=#{redeem_code}</script>")
    void updateRedmmCode(RedeemCodeModel model);

    @Select("<script>select count(0) from redeem_code c where goods_id = #{goods_id} and status = #{status} " +
            "and NOW() > term_of_validity_end GROUP BY goods_id </script>")
    Integer queryByEXCount(@Param("goods_id")Integer goods_id,@Param("status")Integer status);

    @Select("<script>select * from redeem_code c where status = #{status} and NOW() > term_of_validity_end </script>")
    List<RedeemCode> queryByTermOfValidityEnd(@Param("status")Integer status);

    @Select("<script>select count(0) from redeem_code c where goods_id = #{goods_id} GROUP BY goods_id </script>")
    Integer queryByGoods(@Param("goods_id")Integer goods_id);

    @Select("<script>select count(0) from redeem_code c where goods_id = #{goods_id} and status = #{status} GROUP BY goods_id </script>")
    Integer queryByGoodsCount(@Param("goods_id")Integer goods_id,@Param("status")Integer status);


    @Select("<script>select * from redeem_code c where goods_id = #{goods_id} and status = 1 order by id limit 1</script>")
    RedeemCode getReddmCode(@Param("goods_id")Integer goods_id);

    @Delete("<script>delete from redeem_code where id=#{id}</script>")
    void deleteRedmmCode(@Param("id")Integer id);

    @Select("<script>select * from redeem_code c where id = #{id} limit 1</script>")
    RedeemCode getReddmCodeId(@Param("id")Integer id);
}
