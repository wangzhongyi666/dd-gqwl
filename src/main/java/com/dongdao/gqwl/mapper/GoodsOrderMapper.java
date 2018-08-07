package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.GoodsOrder;
import com.dongdao.gqwl.model.GoodsOrderModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsOrderMapper<T> extends BaseMapper {

    @Select("<script>SELECT COUNT(0) FROM goods_order t " +
            "left join redeem_code c on t.redeem_code = c.redeem_code " +
            //"left join sys_vip v on v.id = t.vip_id " +
            "left join goods g on t.goods_id = g.id WHERE 1=1 "+
            "<if test=\"goods_nn != null and goods_nn != ''\"> AND (t.goods_number like CONCAT('%', #{goods_nn}, '%') " +
            "or t.goods_name like CONCAT('%', #{goods_nn}, '%') or t.redeem_code like CONCAT('%', #{goods_nn}, '%') " +
            "or t.deliver_goods_tel like CONCAT('%', #{goods_nn}, '%') or t.onsignee_name like CONCAT('%', #{goods_nn}, '%') " +
            "or t.exchange_number like CONCAT('%', #{goods_nn}, '%')) </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.exchange_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.exchange_time </if> "+
            "<if test=\"startTime != null and startTime != ''\"> AND t.write_off_time >= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> AND #{endTime} >= t.write_off_time </if> "+
            "<if test=\"write_off_state != null and write_off_state != 0\">AND t.write_off_state = #{write_off_state} </if>" +
            "<if test=\"status != null and status != ''\"> AND c.status = #{status} </if>"+
            "<if test=\"take_type != null and take_type != ''\"> AND g.take_type = #{take_type} </if>"+
            "<if test=\"deptId != null and deptId != ''\"> AND g.deptId = #{deptId} </if>"+
            "</script>")
    Integer queryByCount(GoodsOrderModel model);

    @Select("<script>SELECT t.*,c.status FROM goods_order t " +
            "left join redeem_code c on t.redeem_code = c.redeem_code " +
           // "left join sys_vip v on v.id = t.vip_id " +
            "left join goods g on t.goods_id = g.id WHERE 1=1 "+
            "<if test=\"goods_nn != null and goods_nn != ''\"> AND (t.goods_number like CONCAT('%', #{goods_nn}, '%') " +
            "or t.goods_name like CONCAT('%', #{goods_nn}, '%') or t.redeem_code like CONCAT('%', #{goods_nn}, '%') " +
            "or t.deliver_goods_tel like CONCAT('%', #{goods_nn}, '%') or t.onsignee_name like CONCAT('%', #{goods_nn}, '%') " +
            "or t.exchange_number like CONCAT('%', #{goods_nn}, '%')) </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.exchange_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.exchange_time </if> "+
            "<if test=\"startTime != null and startTime != ''\"> AND t.write_off_time >= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> AND #{endTime} >= t.write_off_time </if> "+
            "<if test=\"write_off_state != null and write_off_state != 0\">AND t.write_off_state = #{write_off_state} </if>" +
            "<if test=\"status != null and status != ''\"> AND c.status = #{status} </if>"+
            "<if test=\"take_type != null and take_type != ''\"> AND g.take_type = #{take_type} </if>"+
            "<if test=\"deptId != null and deptId != ''\"> AND g.deptId = #{deptId} </if>"+
            "order by t.id desc limit #{num1},#{num2}</script>")
    List<GoodsOrder> queryByList(GoodsOrderModel model);

    @Select("<script>SELECT COUNT(0) FROM goods_order t left join goods g on g.id = r.goods_id WHERE g.take_type = 2 "+
            "<if test=\"goods_nn != null and goods_nn != ''\"> AND (t.goods_number like CONCAT('%', #{goods_nn}, '%') " +
            "or t.goods_name like CONCAT('%', #{goods_nn}, '%') or t.deliver_goods_tel like CONCAT('%', #{deliver_goods_tel}, '%')) </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.exchange_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.exchange_time </if> "+
            "<if test=\"startTime != null and startTime != ''\"> AND t.write_off_time >= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> AND #{endTime} >= t.write_off_time </if> "+
            "<if test=\"write_off_state != null and write_off_state != 0\">AND t.write_off_state = #{write_off_state} </if>" +
            "<if test=\"deptId != null and deptId != ''\"> AND g.deptId = #{deptId} </if>"+
            "</script>")
    Integer queryBySendCount(GoodsOrderModel model);

    @Select("<script>SELECT * FROM goods_order t left join goods g on g.id = r.goods_id WHERE g.take_type = 2  "+
            "<if test=\"goods_nn != null and goods_nn != ''\"> AND (t.goods_number like CONCAT('%', #{goods_nn}, '%') " +
            "or t.goods_name like CONCAT('%', #{goods_nn}, '%') or t.deliver_goods_tel like CONCAT('%', #{deliver_goods_tel}, '%')) </if>"+
            "<if test=\"startDate != null and startDate != ''\"> AND t.exchange_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.exchange_time </if> "+
            "<if test=\"startTime != null and startTime != ''\"> AND t.write_off_time >= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> AND #{endTime} >= t.write_off_time </if> "+
            "<if test=\"write_off_state != null and write_off_state != 0\">AND t.write_off_state = #{write_off_state} </if>" +
            "<if test=\"deptId != null and deptId != ''\"> AND g.deptId = #{deptId} </if>"+
            "order by t.id desc limit #{num1},#{num2}</script>")
    List<GoodsOrder> queryBySendList(GoodsOrderModel model);

    /**
     * 导出兑换列表
     * @param model
     * @return
     */
    @Select("<script>SELECT *,CASE t.write_off_state WHEN 1 THEN '未兑换' ELSE '已兑换' END AS write_off_info FROM goods_order t " +
            "left join goods g on t.goods_id = g.id WHERE 1=1 "+
            "<if test=\"startDate1 != null and startDate1 != ''\"> AND t.exchange_time >= #{startDate1} </if>"+
            "<if test=\"endDate1 != null and endDate1 != ''\"> AND #{endDate1} >= t.exchange_time </if> "+
            "<if test=\"deptId != null and deptId != ''\"> AND g.deptId = #{deptId} </if>"+
            "order by  t.exchange_time desc</script>")
    List<GoodsOrder> queryByExcel(GoodsOrderModel model);

    /**
     * 兑换商品列表
     * @param model
     * @return
     */
    @Select("<script>SELECT t.*,CASE t.write_off_state WHEN 1 THEN '待使用' WHEN 2 THEN '已使用' ELSE '过期' END AS write_off_info " +
            ",concat(g.term_of_validity_start,'至',term_of_validity_end) as term_of_validity_time "+
            ",term_of_validity_start,term_of_validity_end  FROM goods_order t " +
            "left join goods g on t.goods_id = g.id WHERE 1=1 "+
            "<if test=\"startDate1 != null and startDate1 != ''\"> AND t.exchange_time >= #{startDate1} </if>"+
            "<if test=\"endDate1 != null and endDate1 != ''\"> AND #{endDate1} >= t.exchange_time </if> "+
            "<if test=\"take_type != null and take_type != ''\"> AND g.take_type = #{take_type} </if> "+
            "<if test=\"vip_id != null and vip_id != ''\"> AND t.vip_id = #{vip_id} </if> "+
            "<if test=\"write_off_state != null and write_off_state != 0\"> AND t.write_off_state = #{write_off_state} </if>"+
            "<if test=\"deptId != null and deptId != ''\"> AND g.deptId = #{deptId} </if>"+
            "order by t.exchange_time desc</script>")
    List<GoodsOrder> queryBySelfList(GoodsOrderModel model);

    /**
     * 兑换商品列表
     * @param model
     * @return
     */
    @Select("<script>SELECT t.*,CASE t.write_off_state WHEN 1 THEN '待发货' WHEN 2 THEN '已发货' ELSE '过期' END AS write_off_info  FROM goods_order t " +
            "left join goods g on t.goods_id = g.id WHERE 1=1 "+
            "<if test=\"startDate1 != null and startDate1 != ''\"> AND t.exchange_time >= #{startDate1} </if>"+
            "<if test=\"endDate1 != null and endDate1 != ''\"> AND #{endDate1} >= t.exchange_time </if> "+
            "<if test=\"take_type != null and take_type != ''\"> AND g.take_type = #{take_type} </if> "+
            "<if test=\"write_off_state != null and write_off_state != 0\"> AND t.write_off_state = #{write_off_state} </if>"+
            "<if test=\"vip_id != null and vip_id != ''\"> AND t.vip_id = #{vip_id} </if> "+
            "order by t.exchange_time desc</script>")
    List<GoodsOrder> queryByMailList(GoodsOrderModel model);

    /**
     * 导出核销查询列表
     * @param model
     * @return
     */
    @Select("<script>SELECT *,CASE t.write_off_state WHEN 1 THEN '未发送' ELSE '已发送' END AS stateCh_zn FROM goods_order t WHERE 1=1 "+
            "<if test=\"startDate != null and startDate != ''\"> AND t.write_off_time >= #{startDate} </if>"+
            "<if test=\"endDate != null and endDate != ''\"> AND #{endDate} >= t.write_off_time </if> "+
            "order by  t.exchange_time desc</script>")
    List<GoodsOrder> queryBySendExcel(GoodsOrderModel model);

    @Select("<script>select count(1) from goods_order t left join goods g on t.goods_number=g.number where 1=1" +
            "<if test=\"goods_name != null and goods_name != ''\"> AND t.goods_name = #{goods_name} </if>" +
            "<if test=\"goods_id != null and goods_id != 0\"> AND t.goods_id = #{goods_id} </if>" +
            "<if test=\"vip_id != null and vip_id != ''\"> AND t.vip_id = #{vip_id} </if>" +
            "<if test=\"startTime != null and startTime != ''\"> AND t.exchange_time >= #{startTime} </if>" +
            "<if test=\"endTime != null and endTime != ''\"> AND #{endTime}>=t.exchange_time  </if>" +
            "<if test=\"take_type != null and take_type != ''\"> AND g.take_type=#{take_type}  </if>" +
            "<if test=\"take_type=1 and write_off_state1 != null and write_off_state1 != ''\"> AND t.write_off_state=#{write_off_state1}  </if>" +
            "<if test=\"take_type=2 and write_off_state2 != null and write_off_state2 != ''\"> AND t.write_off_state=#{write_off_state2}  </if>" +
            "</script>")
    Integer countGoodslist(GoodsOrderModel model);

    @Select("<script>select t.*,r.term_of_validity_start,r.term_of_validity_end,g.info,g.imgUrl,g.take_type,DATE_FORMAT(t.exchange_time,'%Y-%m-%d %H:%i:%s') as exchange_time_str from goods_order " +
            "t left join goods g on t.goods_number=g.number left join redeem_code r on t.redeem_code=r.redeem_code where 1=1 " +
            "<if test=\"goods_name != null and goods_name != ''\"> AND t.goods_name = #{goods_name} </if>" +
            "<if test=\"goods_id != null and goods_id != 0\"> AND t.goods_id = #{goods_id} </if>" +
            "<if test=\"vip_id != null and vip_id != ''\"> AND t.vip_id = #{vip_id} </if>" +
            "<if test=\"startTime != null and startTime != ''\"> AND t.exchange_time >= #{startTime} </if>" +
            "<if test=\"endTime != null and endTime != ''\"> AND #{endTime}>=t.exchange_time  </if>" +
            "<if test=\"take_type != null and take_type != 0\"> AND g.take_type=#{take_type}  </if>" +
            "<if test=\"take_type=1 and write_off_state1 != null and write_off_state1 != ''\"> AND t.write_off_state=#{write_off_state1}  </if>" +
            "<if test=\"take_type=2 and write_off_state2 != null and write_off_state2 != ''\"> AND t.write_off_state=#{write_off_state2}  </if>" +
            "</script>")
    List<GoodsOrder> getGoodlist(GoodsOrderModel model);

    @Insert("insert into goods_order(goods_id,goods_number,goods_name,order_number,redeem_code,nums,integral,exchange_number," +
            "exchange_time,write_off_time,write_off_state,deliver_goods_address,vip_id,vname,deliver_goods_tel,onsignee_name,create_time,address_id) values("+
            "#{goods_id},#{goods_number},#{goods_name},#{order_number},#{redeem_code},#{nums},#{integral},#{exchange_number},#{exchange_time}," +
            "#{write_off_time},#{write_off_state},#{deliver_goods_address},#{vip_id},#{vname},#{deliver_goods_tel},#{onsignee_name},#{create_time},#{address_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addGoodsOrder(GoodsOrderModel model);

    @Select("<script>select *,g.info,g.statement,concat(g.term_of_validity_start,'至',term_of_validity_end) as term_of_validity_time" +
            ",CASE t.write_off_state WHEN 1 THEN '待发货' WHEN 2 THEN '已发货' ELSE '过期' END AS write_off_info "+
            " from goods_order t " +
            " left join goods g on t.goods_number=g.number where 1=1" +
            "<if test=\"id != null\"> AND t.id = #{id} </if>" +
            "</script>")
    GoodsOrder queryByOrderId(@Param("id")Integer id);

    @Select("<script>select count(1) from goods_order t left join goods g on t.goods_number=g.number where 1=1" +
            "<if test=\"goods_name != null and goods_name != ''\"> AND t.goods_name = #{goods_name} </if>" +
            "<if test=\"vip_id != null and vip_id != 0\"> AND t.vip_id = #{vip_id} </if>" +
            "<if test=\"goods_id != null and goods_id != 0\"> AND t.goods_id = #{goods_id} </if>" +
            "<if test=\"deptId != null and deptId != 0\"> AND g.deptId = #{deptId} </if>" +
            "<if test=\"take_type != null and take_type != 0\"> AND g.take_type=#{take_type} </if>" +
            "<if test=\"write_off_state != null and write_off_state != 0\"> AND t.write_off_state=#{write_off_state} </if>" +
            " group by t.goods_id </script>")
    Integer countGoods(GoodsOrderModel model);

    @Select("<script>select count(1) from goods_order t left join goods g on t.goods_number=g.number where 1=1" +
            "<if test=\"goods_name != null and goods_name != ''\"> AND t.goods_name = #{goods_name} </if>" +
            "<if test=\"vip_id != null and vip_id != 0\"> AND t.vip_id = #{vip_id} </if>" +
            "<if test=\"goods_id != null and goods_id != 0\"> AND t.goods_id = #{goods_id} </if>" +
            "<if test=\"deptId != null and deptId != 0\"> AND g.deptId = #{deptId} </if>" +
            "<if test=\"take_type != null and take_type != 0\"> AND g.take_type=#{take_type} </if>" +
            "<if test=\"write_off_state != null and write_off_state != 0\"> AND t.write_off_state=#{write_off_state} </if>" +
            "</script>")
    Integer countGoods1(GoodsOrderModel model);


    @Update("<script>update goods_order set write_off_state = 2,waybill_number = #{waybill_number} , courier_firm = #{courier_firm} , write_off_time = now() where id=#{order_id}</script>")
    void updateSendGoods(@Param("waybill_number")String waybill_number,@Param("courier_firm")String courier_firm,@Param("order_id")Integer order_id);


    @Select("<script>select * from goods_order t " +
            "<if test=\"order_number != null and order_number != ''\"> AND t.order_number = #{order_number} </if>" +
            "</script>")
    GoodsOrder queryByOrderNumber(@Param("order_number")String order_number);


    @Update("<script>update goods_order set payment = #{payment} where order_number=#{order_number}</script>")
    void updatePayment(@Param("payment")Double payment,@Param("order_number")String order_number);

}
