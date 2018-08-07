package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.Order;
import com.dongdao.gqwl.bean.OrderInfo;
import com.dongdao.gqwl.model.IntegrationModel;
import com.dongdao.gqwl.model.OrderInfoModel;
import com.dongdao.gqwl.model.OrderModel;
import com.dongdao.gqwl.model.QuitModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FinanceMapper {
    @Select("<script> select count(1) from sys_order t left join sys_dept d on t.deptId=d.deptId left join sys_vip v on t.uid=v.id " +
            " where t.audit >=1 " +
            "<if test=\"name != null and name != ''\"> and v.name = #{name} or v.tel = #{name} or t.orderNum = #{name} </if>" +
            "<if test=\"tel != null and tel != ''\"> and v.tel=#{tel} </if>" +
            "<if test=\"insuranceType != null and insuranceType != ''\"> and t.insuranceType=#{insuranceType} </if>" +
            "<if test=\"orderNum != null and orderNum != ''\"> and t.orderNum=#{orderNum} </if>" +
            "<if test=\"rechargeTime1 != null and rechargeTime1 != ''\"> and t.rechargeTime>#{rechargeTime1} </if>" +
            "<if test=\"rechargeTime2 != null and rechargeTime2 != ''\"> and #{rechargeTime2}>t.rechargeTime </if>" +
            "<if test=\"insuStart != null and insuStart != ''\"> and t.insuStart>=#{insuStart} </if>"+
            "<if test=\"insuEnd != null and insuEnd != ''\"> and #{insuEnd}>=t.insuEnd </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if></script>")
    Integer countOrderlist(OrderModel model);

    @Select("<script> select t.*,v.name,v.tel,d.name as deptName from sys_order t left join sys_dept d on t.deptId=d.deptId left join sys_vip v on t.uid=v.id " +
            " where t.audit >=1 " +
            "<if test=\"name != null and name != ''\"> and v.name = #{name} or v.tel = #{name} or t.orderNum = #{name} </if>" +
            "<if test=\"tel != null and tel != ''\"> and v.tel=#{tel} </if>" +
            "<if test=\"insuranceType != null and insuranceType != ''\"> and t.insuranceType=#{insuranceType} </if>" +
            "<if test=\"orderNum != null and orderNum != ''\"> and t.orderNum=#{orderNum} </if>" +
            "<if test=\"rechargeTime1 != null and rechargeTime1 != ''\"> and t.rechargeTime>#{rechargeTime1} </if>" +
            "<if test=\"rechargeTime2 != null and rechargeTime2 != ''\"> and #{rechargeTime2}>t.rechargeTime </if>" +
            "<if test=\"insuStart != null and insuStart != ''\"> and t.insuStart>=#{insuStart} </if>"+
            "<if test=\"insuEnd != null and insuEnd != ''\"> and #{insuEnd}>=t.insuEnd </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if>" +
            "order by auditTime desc  <if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2} </if> </script>")
    List<Order> getOrderlist(OrderModel model);
    @Select("<script> select t.orderNum,v.name,v.tel,CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS inTypeStr," +
            "concat(t.insuStart,'-',t.insuEnd) as insuStart,t.payment,t.payment * 0.006 as poundage,t.rechargeTime from sys_order t left join sys_dept d on t.deptId=d.deptId left join sys_vip v on t.uid=v.id " +
            " where 1=1 " +
            "<if test=\"rechargeTime1 != null and rechargeTime1 != ''\"> and t.rechargeTime>#{rechargeTime1} </if>" +
            "<if test=\"rechargeTime2 != null and rechargeTime2 != ''\"> and #{rechargeTime2}>t.rechargeTime </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if></script>")
    List<Order> queryByExcel(OrderModel model);
    @Select("<script> select sum(payment) as payment,insuranceType from sys_order_info" +
            " where 1=1  and audit>=11 <if test=\"deptId != null and deptId != 0\"> and deptId = #{deptId}</if>"+
            "<if test=\"status != null and status != ''\">and status = #{status}</if>"+
            "<if test=\"inseuranceCycle != null and inseuranceCycle != ''\">and inseuranceCycle = #{inseuranceCycle}</if>"+
            "<if test=\"insuStart != null and insuStart != ''\">and SUBSTRING(inseuranceCycle,1,4)= #{insuStart}</if>"+
            " group by insuranceType order by insuranceType</script>")
    List<Map> CountCPayment(OrderInfoModel model);

    @Select("<script> select sum(unitPrice) as unitPrice,insuranceType from sys_order_info" +
            " where 1=1 and status=2 and audit>=11 <if test=\"deptId != null and deptId != 0\"> and deptId = #{deptId}</if>" +
            "<if test=\"inseuranceCycle != null and inseuranceCycle != ''\">and #{inseuranceCycle}<![CDATA[ >= ]]>insuStart and insuEnd <![CDATA[ >= ]]> #{inseuranceCycle}</if>"+
            " group by insuranceType order by insuranceType</script>")
    List<Map> CountBPayment1(OrderInfoModel model);

    @Select("<script> select unitPrice,insuStart,insuEnd,payment,insuranceType from sys_order_info" +
            " where status=2 and audit>=11 <if test=\"deptId != null and deptId != 0\"> and deptId = #{deptId}</if>" +
            "<if test=\"insuStart != null and insuStart != ''\">and #{insuStart}<![CDATA[ >= ]]>SUBSTRING(insuStart,1,4) and SUBSTRING(insuEnd,1,4)<![CDATA[ >= ]]> #{insuStart}</if>"+
            "</script>")
    List<OrderInfo> CountBPayment2(OrderInfoModel model);


    @Select("<script>select sum(integration) as integration from sys_integration</script>")
    Integer countInye();


    @Select("<script>select sum(integration) as integration from sys_integration_log where 1=1 " +
            "<if test=\"type != null and type != 0\"> and type = #{type}</if></script>")
    Integer countInLog(@Param("type")Integer type);

    @Select("<script>select count(1) from sys_integration_log where 1=1 " +
            "<if test=\"uid != null and uid != 0\"> and uid = #{uid}</if></script>")
    Integer countInteInfo(IntegrationModel model);
    @Select("<script>select * from sys_integration_log where 1=1 " +
            "<if test=\"uid != null and uid != 0\"> and uid = #{uid}</if>" +
            " order by addTime desc " +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2} </if></script>")
    List<Map<String,Object>> getInteInfo(IntegrationModel model);
    @Select("<script>select t.*,a.monthNum,a.onepay,d.name as deptName from sys_order t left join " +
            "(select count(1) as monthNum,i.orderNum,i.payment as onepay from sys_order_info i where i.orderNum=#{orderNum} and i.audit=31 and status=1 ) a " +//and i.`status`=1
            "on t.orderNum=a.orderNum left join sys_dept d on t.deptId=d.deptId where t.orderNum=#{orderNum}</script>")
    List<Map<String,Object>> getOrderByOrderNum(@Param("orderNum")String orderNum);
    @Select("<script>select * from sys_order_info where orderNum=#{orderNum} and status=2 and audit=31</script>")
    List<OrderInfo> getOrdeInfoByOrderNum(String orderNum);
    @Select("<script>select * from sys_quit where orderNum=#{orderNum}</script>")
    List<Map<String,Object>> getQuitByOrderNum(@Param("orderNum") String orderNum);
    @Select("<script>select * from sys_integration where uid=#{uid}</script>")
    List<Map<String,Object>> getJfye(@Param("uid") Integer uid);

    @Select("<script>select sum(pra_payment) as pra_payment,insuranceType from sys_quit where 1=1 " +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and subTime > #{subTime1}</if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}> subTime</if>" +
            "group by insuranceType order by insuranceType</script>")
    List<Map> countQuit(QuitModel model);
    @Select("<script>select sum(payment_inte) as payment_inte,insuranceType from sys_quit where 1=1 " +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and subTime > #{subTime1}</if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}> subTime</if>" +
            "group by insuranceType order by insuranceType</script>")
    List<Map> countQuit1(QuitModel model);

    @Select("<script>select * from sys_order where orderNum=#{orderNum}</script>")
    Order queryOrderByOrderNum(@Param("orderNum") String orderNum);
}
