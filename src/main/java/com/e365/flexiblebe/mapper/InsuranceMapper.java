package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.*;
import com.e365.flexiblebe.model.InsuranceModel;
import com.e365.flexiblebe.model.OrderInfoModel;
import com.e365.flexiblebe.model.OrderModel;
import com.e365.flexiblebe.model.QuitModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface InsuranceMapper {
    @Select("<script>select count(1) from sys_order t left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.audit=2" +
            "<if test=\"uid != null and uid != 0\"> and t.uid = #{uid}</if>" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"deptId != null and deptId !=0\"> and t.deptId = #{deptId}</if>" +
            "<if test=\"insuStart1 != null and insuStart1 != ''\"> and #{insuStart1} >= t.insuStart </if>" +
            "<if test=\"insuEnd1 != null and insuEnd1 != ''\"> and t.insuEnd >= #{insuEnd1}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "</script>")
    Integer countadjulist(OrderModel model);
    @Select("<script> select t.*,v.name,v.tel,i.insuranceNum,v.identNum from sys_order t left join sys_vip v on t.uid=v.id  " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.audit=2 " +
            "<if test=\"uid != null and uid != 0\"> and t.uid = #{uid}</if>" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"ratio != null and ratio != 0\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"deptId != null and deptId !=0\"> and t.deptId = #{deptId}</if>" +
            "<if test=\"insuStart1 != null and insuStart1 != ''\"> and #{insuStart1} >= t.insuStart </if>" +
            "<if test=\"insuEnd1 != null and insuEnd1 != ''\"> and t.insuEnd >= #{insuEnd1}</if>" +
            "order by subTime desc" +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>" +
            "</script>")
    List<Order> getadjulist(OrderModel model);
    @Select("<script>select count(1) from sys_order_info t left join sys_vip v on t.uid=v.id left join sys_order s on t.orderNum= s.orderNum" +
            " where t.isquit!=1 and t.audit>1" +
            "<if test=\"status != null and status != 0\"> and t.status = #{status}</if>" +
            "<if test=\"uid != null and uid != 0\"> and t.uid = #{uid}</if>" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"audit != null and audit != 0 and audit != 201\"> and t.audit = #{audit} </if>" +
            "<if test=\"status != null and status != 0\"> and t.status = #{status} </if>" +
            "<if test=\"insuStart1 != null and insuStart1 != ''\"> and (t.inseuranceCycle>=#{insuStart1} or (#{insuStart1} >= t.insuStart and t.insuEnd >= #{insuEnd1}))</if>" +
            "<if test=\"insuEnd1 != null and insuEnd1 != ''\"> and (#{insuEnd1}>=t.inseuranceCycle or (#{insuStart1} >= t.insuStart and t.insuEnd >= #{insuEnd1}))</if>" +
            "<if test=\"audit == 201\"> and t.audit in(11,21)</if>" +
            "<if test=\"deptId != null and deptId !=0\"> and t.deptId = #{deptId}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "</script>")
    Integer countinsulist(OrderInfoModel model);

    @Select("<script> select t.*,v.name,v.tel,i.insuranceNum,v.identNum from sys_order_info t left join sys_vip v on t.uid=v.id  " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.isquit!=1 and t.audit>1" +
            "<if test=\"status != null and status != 0\"> and t.status = #{status}</if>" +
            "<if test=\"uid != null and uid != 0\"> and t.uid = #{uid}</if>" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"ratio != null and ratio != 0\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"audit != null and audit != 0 and audit != 101 and audit != 102\"> and t.audit = #{audit} </if>"+
            "<if test=\"status != null and status != 0\"> and t.status = #{status} </if>" +
            "<if test=\"insuStart1 != null and insuStart1 != ''\"> and (t.inseuranceCycle>=#{insuStart1} or (#{insuStart1} >= t.insuStart and t.insuEnd >= #{insuEnd1}))</if>" +
            "<if test=\"insuEnd1 != null and insuEnd1 != ''\"> and (#{insuEnd1}>=t.inseuranceCycle or (#{insuStart1} >= t.insuStart and t.insuEnd >= #{insuEnd1}))</if>" +
            "<if test=\"rank != null and rank == 1 \"> order by inseuranceCycle </if>" +
            "<if test=\"rank != null and rank == 2 \"> order by inseuranceCycle desc </if>" +
            "<if test=\"deptId != null and deptId !=0\"> and t.deptId = #{deptId}</if>" +
            "<if test=\"audit == 101\"> and t.audit = 41</if>" +
            "<if test=\"audit == 102\"> and t.audit != 41</if>" +
            " group by t.id "+
            "<if test=\"rank != 1 or rank != 2 \"> order by subTime desc </if>" +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>" +

            "</script>")
    List<OrderInfo> getinsulist(OrderInfoModel model);

    @Update("<script> update sys_order t set audit=#{audit} " +
            "<if test=\"auditTime != null and auditTime != ''\"> ,t.auditTime=#{auditTime} </if>  " +
            "<if test=\"insuStart != null and insuStart!=''\"> ,t.insuStart = #{insuStart}</if>" +
            "<if test=\"insuEnd != null and insuEnd!=''\"> ,t.insuEnd = #{insuEnd}</if>" +
            "<if test=\"payment != null and payment!=0.0\">,t.payment = #{payment}</if>" +
            " where id=#{id} </script>")
    void upOrderAudit(OrderModel model);

    @Update("<script> update sys_order_info t set audit=#{audit} " +
            "<if test=\"auditTime != null and auditTime != ''\"> ,t.auditTime=#{auditTime} </if>  where id=#{id} </script>")
    void upAudit(OrderInfoModel model);
    //审核状态:(11:待财务审核;21:财务审核通过(待城市经理审核);22:财务驳回;31:城市经理审核通过;32:城市经理驳回;)
    @Select("<script> select t.orderNum,i.insuranceNum,v.name,v.identNum,v.tel,CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS inTypeStr," +
            " t.inseuranceCycle,concat(t.insuStart,'-',t.insuEnd) as insuStart,t.base,t.ratio,t.payment,t.subTime," +
            " CASE t.audit WHEN 11 THEN '待审核' when 21 then '已通过' when 22 then '已驳回' ELSE '已通过' END as audit1," +
            " CASE t.audit WHEN 21 THEN '待审核' when 21 then '已通过' when 32 then '已驳回' when 22 then '' ELSE '待审核' END as audit2, " +
            " CASE t.audit WHEN 11 THEN '待财务审核' when 22 then '财务驳回' when 31 then '城市经理审核通过' when 32 then '城市经理驳回' when 41 then '已缴纳' ELSE '' END as audit3 " +
            " from sys_order_info t left join sys_dept d on t.deptId=d.deptId left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where isquit!=1  and t.audit>1" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "<if test=\"status != null and status != 0\"> and t.status = #{status}</if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if></script>")
    List<OrderInfo> queryByExcel(OrderInfoModel model);

    @Select("<script> select t.orderNum,i.insuranceNum,v.name,v.identNum,v.tel,CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS inTypeStr," +
            " concat(t.insuStart,'-',t.insuEnd) as insuStart,t.base,t.ratio,t.payment,t.subTime," +
            " CASE t.audit WHEN 2 THEN '待审核' end as audit1 " +
            " from sys_order t left join sys_dept d on t.deptId=d.deptId left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where 1=1" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if></script>")
    List<Order> queryOrderByExcel(OrderModel model);

    @Select("<script> select count(1) from sys_quit t left join sys_vip v on t.uid=v.id " +
            " where 1=1" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.identNum = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "<if test=\"quitMonth != null and quitMonth != ''\"> and t.quitMonth LIKE CONCAT(CONCAT('%',#{quitMonth},'%')) </if>"+
            "<if test=\"audit != null and audit != 0 and audit != 201 and audit != 101\"> and t.audit = #{audit} </if>"+
            "<if test=\"audit != null and audit == 201\"> and t.audit in(11,21) </if>"+
            "<if test=\"audit != null and audit == 101\"> and t.audit in(11,31) </if>"+
            "<if test=\"deptId != null and deptId !=0\"> and t.deptId = #{deptId}</if>" +
            "</script>")
    Integer countquitlist(QuitModel model);

    @Select("<script> select t.*,v.name,v.tel,i.insuranceNum,v.identNum from sys_quit t left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where 1=1" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.identNum = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "<if test=\"quitMonth != null and quitMonth != ''\"> and t.quitMonth LIKE CONCAT(CONCAT('%',#{quitMonth},'%')) </if>"+
            "<if test=\"audit != null and audit != 0 and audit != 201 and audit != 101\"> and t.audit = #{audit} </if>"+
            "<if test=\"audit != null and audit == 201\"> and t.audit in(11,21) </if>"+
            "<if test=\"audit != null and audit == 101\"> and t.audit in(11,31) </if>"+
            "<if test=\"deptId != null and deptId !=0\"> and t.deptId = #{deptId}</if>" +
            " order by subTime desc limit #{num1},#{num2}</script>")
    List<Quit> getquitlist(QuitModel model);
    @Update("<script> update sys_quit set audit=#{audit} where id=#{id} </script>")
    void upQuitAudit(QuitModel model);
    @Select("<script> select t.orderNum,i.insuranceNum,v.name,v.identNum,CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS inTypeStr," +
            " concat(t.insuStart,'-',t.insuEnd) as insuStart,t.quit_payment,t.quit_integration,t.integration_diff,t.payment_inte,t.pra_payment,t.subTime," +
            " CASE t.audit WHEN 11 THEN '待审核' when 21 then '已通过' when 22 then '已驳回' ELSE '已通过' END as audit1," +
            " CASE t.audit WHEN 21 THEN '待审核' when 31 then '已通过' when 32 then '已驳回' when 22 then '已驳回' ELSE '待审核' END as audit2 " +
            " from sys_quit t left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where 1=1 " +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if></script>")
    List<Quit> queryquitByExcel(QuitModel model);

    @Select("<script> select count(1) from sys_order_info t " +
            "left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.isquit!=1 and t.audit=31 " +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or i.insuranceNum = #{name})</if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and (t.inseuranceCycle>=#{subTime1} or (#{subTime1} >= t.insuStart and t.insuEnd >= #{subTime2}))</if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and (#{subTime2}>=t.inseuranceCycle or (#{subTime1} >= t.insuStart and t.insuEnd >= #{subTime2}))</if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"uid != null\"> and uid = #{uid}</if>" +
            "<if test=\"audit == 101\"> and t.audit in (1,11,21,31)</if>" +
            "<if test=\"audit == 102\"> and t.audit in(31,41)</if>" +
            "<if test=\"status != null\"> and t.status = #{status}</if>" +
            "</script>")
    Integer countUserlist(OrderInfoModel model);

    @Select("<script> select t.*,v.name,v.tel,i.insuranceNum,v.identNum from sys_order_info t left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.isquit!=1 and t.audit=31 " +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or i.insuranceNum = #{name})</if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and (t.inseuranceCycle>=#{subTime1} or (#{subTime1} >= t.insuStart and t.insuEnd >= #{subTime2}))</if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and (#{subTime2}>=t.inseuranceCycle or (#{subTime1} >= t.insuStart and t.insuEnd >= #{subTime2}))</if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"audit == 101\"> and t.audit in (1,11,21,31)</if>" +
            "<if test=\"audit == 102\"> and t.audit in(31,41)</if>" +
            "<if test=\"uid != null\"> and uid = #{uid}</if>" +
            "<if test=\"status != null\"> and t.status = #{status}</if>" +
            " order by auditTime desc " +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>" +
            "</script>")
    List<OrderInfo> getUserlist(OrderInfoModel model);

    @Select("<script> select count(1) from sys_order_info t left join sys_vip v on t.uid=v.id " +
            " where t.isquit!=1 " +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name} )</if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.inseuranceCycle>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.inseuranceCycle </if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"uid != null\"> and uid = #{uid}</if>" +
            "<if test=\"audit == 102\"> and t.audit in (1,11,21)</if>" +
            "<if test=\"audit == 101\"> and t.audit = 31</if>" +
            "</script>")
    Integer countUserlist1(OrderInfoModel model);

    @Select("<script> select t.*,v.name,v.tel,i.insuranceNum,v.identNum from sys_order_info t left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.isquit!=1  "+
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType}</if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.inseuranceCycle>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.inseuranceCycle </if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"audit == 102\"> and t.audit in (1,11,21)</if>" +
            "<if test=\"audit == 101\"> and t.audit = 31</if>" +
            "<if test=\"uid != null\"> and uid = #{uid}</if>" +
            " order by auditTime desc " +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>" +
            "</script>")
    List<OrderInfo> getUserlist1(OrderInfoModel model);
    @Select("<script> select i.insuranceNum,v.name,v.identNum,v.tel,CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS inTypeStr," +
            " t.inseuranceCycle,t.base,t.ratio,t.payment,t.auditTime" +
            " from sys_order_info t left join sys_dept d on t.deptId=d.deptId left join sys_vip v on t.uid=v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.status =1 and t.isquit!=1 and t.audit=31 " +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.auditTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.auditTime </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.deptId = #{deptId}</if>" +
            "<if test=\"uid != null\"> and t.uid = #{uid}</if>" +
            " order by auditTime desc</script>")
    List<OrderInfo> queryByUserExcel(OrderInfoModel model);

    @Update("<script> update sys_order_info set isquit=#{isquit} where orderNum=#{orderNum} and 11 > audit </script>")
    void upisquit(@Param("isquit")Integer isquit,@Param("orderNum")String orderNum);

    @Insert("insert into sys_order_info(orderNum,uid,insuranceType,inseuranceCycle,insuStart,insuEnd," +
            "monthNum,subTime,auditTime,base,ratio,payment,unitPrice,status,audit,deptId,isquit) values("+
            "#{orderNum},#{uid},#{insuranceType},#{inseuranceCycle},#{insuStart},#{insuEnd}," +
            "#{monthNum},#{subTime},#{auditTime},#{base},#{ratio},#{payment},#{unitPrice},#{status},#{audit},#{deptId},#{isquit})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addInsInfo(OrderInfoModel model);

    @Update("<script> update sys_order set balance= balance-#{payment} where orderNum=#{orderNum} </script>")
    void upBalance(@Param("payment")double payment, @Param("orderNum")String orderNum);

    @Insert("insert into sys_order(orderNum,uid,insuranceType,insuStart,insuEnd,subTime,rechargeTime," +
            "auditTime,base,ratio,payment,balance,audit,deptId,remarks,monthPayment) values("+
            "#{orderNum},#{uid},#{insuranceType},#{insuStart},#{insuEnd},#{subTime},#{rechargeTime}," +
            "#{auditTime},#{base},#{ratio},#{payment},#{balance},#{audit},#{deptId},#{remarks},#{monthPayment})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addIns(OrderModel model);

    @Select("<script>SELECT t.*,v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime,v.identPicUrl1,v.identPicUrl2, " +
            "d.bank,i.bankNum,i.medicalUrl,v.insuranceNature FROM sys_order_info t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " left join  sys_dept d on t.deptId = d.deptId " +
            " WHERE 1=1 and t.orderNum = #{orderNum} and t.audit=41 order by auditTime desc limit 1</script>")
    OrderInfo queryByOrderNum(@Param("orderNum")String orderNum);

    @Select("<script>SELECT t.*,MAX(t.insuEnd) as lastInsuEnd,MAX(t.inseuranceCycle) as lastInseuranceCycle,v.name," +
            "i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime,v.identPicUrl1,v.identPicUrl2, " +
            "d.bank,i.bankNum,i.medicalUrl,v.insuranceNature FROM sys_order_info t " +
            "LEFT JOIN sys_order o ON t.orderNum = o.orderNum LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " left join  sys_dept d on t.deptId = d.deptId " +
            " WHERE isquit != 1 and o.audit = 6 and t.audit = 31 and t.orderNum = #{orderNum} group by t.uid </script>")
    OrderInfo queryByLastPay(@Param("orderNum")String orderNum);

    @Select("<script>SELECT t.*,MAX(t.insuEnd) as lastInsuEnd,MAX(t.inseuranceCycle) as lastInseuranceCycle,v.name," +
            "i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime,v.identPicUrl1,v.identPicUrl2, " +
            "d.bank,i.bankNum,i.medicalUrl,v.insuranceNature FROM sys_order_info t " +
            "LEFT JOIN sys_order o ON t.orderNum = o.orderNum LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " left join  sys_dept d on t.deptId = d.deptId " +
            " WHERE isquit != 1 and o.audit = 6 and t.audit = 31 and t.uid = #{uid} group by t.uid </script>")
    OrderInfo queryByLastPayUId(@Param("uid")Integer uid);

    @Select("<script>SELECT t.*,b.expends_scale,b.expends_amount FROM insurance t " +
            "LEFT JOIN insurance_base b ON t.id = b.ins_id WHERE 1=1 " +
            "<if test=\"insuranceType != null\"> and t.insuranceType = #{insuranceType} </if>" +
            "<if test=\"deptId != null\"> and t.deptId=#{deptId} </if>" +
            "<if test=\"expends_scale != null \"> and b.expends_scale = #{expends_scale}</if>" +
            "limit 1</script>")
    Insurance calculatePayment(InsuranceModel model);

    @Select("select * from sys_order where uid = #{uid} and insuranceType = #{insuranceType} and deptId=#{deptId} and audit in(1,2) limit 1")
    Order queryByAudit(@Param("uid") Integer uid,@Param("insuranceType") Integer insuranceType,@Param("deptId") Integer deptId);

    //参保
    @Select("select * from sys_order_info i left join sys_order o on i.orderNum = o.orderNum " +
            " where i.uid = #{uid} and i.insuranceType = #{insuranceType} and i.inseuranceCycle = #{inseuranceCycle} " +
            " and o.audit not in(8,10) "+
            " and i.audit in(1,11,21,31) and i.isquit != 1 limit 1")
    OrderInfo queryByInfoAudit(@Param("uid") Integer uid,@Param("insuranceType") Integer insuranceType,@Param("inseuranceCycle")String inseuranceCycle);
    //补缴
    @Select("select * from sys_order_info i left join sys_order o on i.orderNum = o.orderNum " +
            " where i.uid = #{uid} and i.insuranceType = #{insuranceType} and i.status = 2 " +
            " and o.audit not in(8) "+
            " and i.audit in(1,11,21,31) and i.isquit != 1 limit 1")
    OrderInfo queryByInfoAudit2(@Param("uid") Integer uid,@Param("insuranceType") Integer insuranceType);


    @Select("select * from sys_order_info where id=#{id}")
    OrderInfo queryByInfoId(@Param("id")Integer id);

    @Update("<script> update sys_order_info set audit=#{audit} " +
            "<if test=\"auditTime != null and auditTime != ''\"> ,t.auditTime=#{auditTime} </if>  where 1=1 " +
            " <if test=\"audit1 != null and audit1 != ''\"> and audit=#{audit1} </if>"+
            " <if test=\"deptId != null and deptId != ''\"> and deptId=#{deptId} </if>"+
            " <if test=\"InseuranceCycle != null and InseuranceCycle != ''\">and InseuranceCycle=#{InseuranceCycle} </if>"+
            " <if test=\"insuranceType != null and insuranceType != ''\"> insuranceType=#{insuranceType} </if>"+
            " <if test=\"status != null and status != ''\"> status=#{status} </if>"+
            "  </script>")
    void payOrderAll(OrderInfoModel model);
    @Update("<script> update sys_order set audit=#{audit} " +
            "<if test=\"auditTime != null and auditTime != ''\"> ,auditTime=#{auditTime} </if>"+
            "<if test=\"rechargeTime != null and rechargeTime != ''\"> ,rechargeTime=#{rechargeTime} </if>"+
            " where 1=1 " +
            "<if test=\"orderNum != null and orderNum != ''\"> and  orderNum=#{orderNum} </if>"+
            "  </script>")
    void upAuditByOrderNum(OrderModel model);


    @Insert("insert into sys_quit(orderNum,uid,insuranceType,quit_payment," +
            "quit_integration,integration_diff,payment_inte,pra_payment,subTime,audit,deptId,quitType,monthNum,quitMonth,extendMonth) values("+
            "#{orderNum},#{uid},#{insuranceType},#{quit_payment},#{quit_integration},#{integration_diff},#{payment_inte}," +
            "#{pra_payment},#{subTime},#{audit},#{deptId},#{quitType},#{monthNum},#{quitMonth},#{extendMonth})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addQuit(QuitModel model);

    @Select("<script>SELECT t.*,i.integration , " +
            "v.insuranceNature FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id  " +
            "LEFT JOIN sys_integration i ON i.uid = v.id  " +
            " WHERE 1=1 and t.orderNum = #{orderNum}</script>")
    Order queryByOrderNumber(@Param("orderNum")String orderNum);


    @Select("<script> select count(0) from (select count(1) from sys_order_info t " +
            "left join sys_vip v on t.uid=v.id " +
            "left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            "left join sys_order s on t.orderNum= s.orderNum " +
            " where t.isquit!=1 and s.audit=6 " +
            "<if test=\"status != null and status != 0\"> and t.status = #{status}</if>" +
            "<if test=\"uid != null and uid != 0\"> and t.uid = #{uid}</if>" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"tname != null and tname != ''\"> and (v.name = #{tname} or v.tel = #{tname} or t.orderNum = #{tname}  " +
            "or i.insuranceNum = #{tname}  or v.identNum = #{tname})</if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType} </if>" +
            "<if test=\"inseuranceCycle != null and inseuranceCycle != ''\"> and t.inseuranceCycle = #{inseuranceCycle} </if>" +
            "<if test=\"ratio != null and ratio != ''\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"audit != null and audit != 0 and audit != 101 and audit != 102 and audit != 103 \"> and t.audit = #{audit} </if>" +
            "<if test=\"insuStart1 != null and insuStart1 != ''\">and (t.inseuranceCycle>=#{insuStart1} or (#{insuStart1} >= t.insuStart and t.insuEnd >= #{insuStart1}))</if>"+
            "<if test=\"insuEnd1 != null and insuEnd1 != ''\"> and (#{insuEnd1}>=t.inseuranceCycle or (#{insuEnd1} >= t.insuStart and t.insuEnd >= #{insuEnd1}))</if>" +
            "<if test=\"audit == 101\"> and t.audit = 41</if>" +
            "<if test=\"audit == 102\"> and t.audit != 41</if>" +
            "<if test=\"audit == 103\"> and t.audit in(1,22,23) </if>" +
            "<if test=\"subTime1 != null and subTime1 != ''\"> and t.subTime>=#{subTime1} </if>" +
            "<if test=\"subTime2 != null and subTime2 != ''\"> and #{subTime2}>=t.subTime </if>" +
            " group by t.id ) a"+
            "</script>")
    Integer countinsulist1(OrderInfoModel model);
    @Select("<script> select count(1) from sys_order where uid=#{vipId} and audit=2" +
            "</script>")
    Integer countNoPayVipId(@Param("vipId")Integer vipId);

    @Delete("<script> delete from sys_order where id=#{id} and (audit is null or 1>audit)</script>")
    void delOrderById(@Param("id")Integer id);

    @Select("<script> select t.*,v.name,v.tel,i.insuranceNum,v.identNum,s.audit as audit1 from sys_order_info t " +
            "left join sys_vip v on t.uid=v.id left join sys_order s on t.orderNum= s.orderNum" +
            "  left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " where t.isquit!=1 and s.audit=6 " +
            "<if test=\"status != null and status != 0\"> and t.status = #{status}</if>" +
            "<if test=\"uid != null and uid != 0\"> and t.uid = #{uid}</if>" +
            "<if test=\"name != null and name != ''\"> and (v.name = #{name} or v.tel = #{name} or t.orderNum = #{name}) </if>" +
            "<if test=\"tname != null and tname != ''\"> and (v.name = #{tname} or v.tel = #{tname} or t.orderNum = #{tname}  " +
            "or i.insuranceNum = #{tname}  or v.identNum = #{tname})</if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and t.insuranceType = #{insuranceType} </if>" +
            "<if test=\"inseuranceCycle != null and inseuranceCycle != ''\"> and t.inseuranceCycle = #{inseuranceCycle} </if>" +
            "<if test=\"ratio != null and ratio != 0\"> and t.ratio = #{ratio}</if>" +
            "<if test=\"audit != null and audit != 0 and audit != 101 and audit != 102 and audit != 103 \"> and t.audit = #{audit} </if>"+
            "<if test=\"insuStart1 != null and insuStart1 != ''\">and (t.inseuranceCycle>=#{insuStart1} or (#{insuStart1} >= t.insuStart and t.insuEnd >= #{insuStart1}))</if>"+
            "<if test=\"insuEnd1 != null and insuEnd1 != ''\"> and (#{insuEnd1}>=t.inseuranceCycle or (#{insuEnd1} >= t.insuStart and t.insuEnd >= #{insuEnd1}))</if>" +
            "<if test=\"audit == 101\"> and t.audit = 41 </if>" +
            "<if test=\"audit == 102\"> and t.audit != 41 </if>" +
            "<if test=\"audit == 103\"> and t.audit in(1,22,23) </if>" +
            "<if test=\"audit1 != null\"> and s.audit = #{audit1} </if>" +
            " group by t.id "+
            "<if test=\"rank == null or rank == 0\"> order by s.subTime desc </if>" +
            "<if test=\"rank != null and rank == 1 \"> order by t.inseuranceCycle </if>" +
            "<if test=\"rank != null and rank == 2 \"> order by t.inseuranceCycle desc </if>" +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>" +
            "</script>")
    List<OrderInfo> getinsulist1(OrderInfoModel model);
    @Select("<script> select t.*,i.* from insurance t left join insurance_base i on t.id =i.ins_id where deptId=#{deptId} order by expends_scale"+
            "</script>")
    List<Map<String,Object>> getbaseByDeptId( @Param("deptId") Integer deptId);

    @Select("select * from sys_order_info where orderNum = #{orderNum} and isquit != 1 and audit in(1,11,21,22,32)")
    List<OrderInfo> queryByOrderNumList(@Param("orderNum")String orderNum);

    @Select("select * from sys_quit where id = #{id}")
    QuitModel queryByQuitId(@Param("id")Integer id);
    @Select("select t.*,v.tel from sys_order t left join sys_vip v on v.id = t.uid  where t.id = #{id}")
    Order queryOrderById(@Param("id")Integer id);
    @Select("select * from sys_insurance where vip_id = #{vipId} and deptId=#{deptId}")
    List<SysInsurance> getYBKById(@Param("vipId")Integer vipId, @Param("deptId")Integer deptId);
    @Update("update sys_order set code_url=#{code_url} where orderNum = #{orderNum}")
    void updateCodeurl(@Param("orderNum")String orderNum,@Param("code_url")String code_url);
    @Select("select * from sys_order where uid=#{uid} and deptId=#{deptId} and insuStart=#{insuStart} and insuEnd=#{insuEnd} and audit=0 and code_url is not null" +
            " and base=#{base} and ratio=#{ratio}")
    List<Order> queryCodeurlByvipId(OrderModel model1);
}
