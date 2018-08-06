package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.*;
import com.e365.flexiblebe.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InsuranceSetMapper<T> extends BaseMapper {

    @Update("<script> update insurance set id=id" +
            "<if test=\"insurance != null and insurance != ''\"> ,insurance=#{insurance} </if>" +
            "<if test=\"expends_base != null and expends_base != 0\"> ,expends_base=#{expends_base} </if>" +
            "<if test=\"effect_start_time != null and effect_start_time != ''\"> ,effect_start_time=#{effect_start_time} </if>" +
            "<if test=\"effect_end_time != null and effect_end_time != ''\"> ,effect_end_time=#{effect_end_time} </if>" +
            "<if test=\"adjustment_time_start != null and adjustment_time_start != ''\"> ,adjustment_time_start=#{adjustment_time_start} </if>" +
            "<if test=\"adjustment_time_end != null and adjustment_time_end != ''\"> ,adjustment_time_end=#{adjustment_time_end} </if>" +
            "<if test=\"state != null and state != 0\"> ,state=#{state} </if>" +
            " where id=#{id} </script>")
    void setInsurance(InsuranceModel model);

    @Update("<script> update insurance_base set ins_id=ins_id " +
            "<if test=\"expends_scale != null and expends_scale != 0\"> ,expends_scale=#{expends_scale} </if>" +
            "<if test=\"expends_amount != null and expends_amount != 0\"> ,expends_amount=#{expends_amount} </if> " +
            "where ins_id=#{id} and expends_scale=#{expends_scale}</script>")
    void updateInsBase(InsuranceModel model);

    @Select("<script>SELECT * FROM insurance i " +
            "LEFT JOIN (select b.ins_id,group_concat(expends_scale) as expends_scale," +
            "group_concat(expends_amount) as expends_amount from insurance_base b GROUP BY b.ins_id) as ib " +
            "ON i.id = ib.ins_id where i.deptId = #{deptId}</script>")
    List<Insurance> allList(@Param("deptId")Integer deptId);

    @Select("<script>SELECT t.*,v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime,v.identPicUrl1,v.identPicUrl2, " +
            "d.bank,i.bankNum,v.insuranceNature,v.deptId,v.city,v.province,i.medicalUrl FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " left join sys_dept d on t.deptId= d.deptId " +
            "WHERE t.audit>=1 " +
            "<if test=\"uid != null and uid != ''\">AND t.uid = #{uid}</if> " +
            "<if test=\"deptId != null and deptId != 0\">AND t.deptId = #{deptId}</if> " +
            "<if test=\"tname != null and tname != ''\">AND (v.`name` LIKE CONCAT('%', #{ tname }, '%') or " +
            " v.`tel` LIKE CONCAT('%', #{ tname }, '%') or v.`identNum` LIKE CONCAT('%', #{ tname }, '%') " +
            " or i.`insuranceNum` LIKE CONCAT('%', #{ tname }, '%') or t.`orderNum` LIKE CONCAT('%', #{ tname }, '%'))</if> " +
            "<if test=\"insuStart != null and insuStart != ''\">AND t.insuStart >= #{insuStart}</if> " +
            "<if test=\"insuEnd != null and insuEnd != ''\">AND #{insuEnd} >= t.insuEnd</if> " +
            "<if test=\"subStartTime != null and subStartTime != ''\">AND t.subTime >= #{subStartTime}</if> " +
            "<if test=\"subEndTime != null and subEndTime != ''\">AND #{subEndTime} >= t.subTime</if> " +
            "<if test=\"insuranceType != null and insuranceType != 0\">AND t.insuranceType = #{insuranceType}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature != 0 and insuranceNature != 3\">AND v.insuranceNature = #{insuranceNature}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature == 3\">AND v.insuranceNature not in(1,2)</if> " +
            "<if test=\"ratio != null and ratio != ''\">AND t.ratio = #{ratio}</if> " +
            "<if test=\"audit != null and audit != 0 and audit != 201\">AND t.audit = #{audit}</if>"+
            "<if test=\"audit1 != null and audit1 == 201\">AND t.audit >=6 </if>"+
            "order by subTime desc,t.id desc limit #{num1},#{num2}</script>")
    List<Order> insuranceDateList(OrderModel model);


    @Select("<script>SELECT count(0) FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            "left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            "WHERE t.audit>=1 " +
            "<if test=\"uid != null and uid != ''\">AND t.uid = #{uid}</if> " +
            "<if test=\"deptId != null and deptId != 0\">AND t.deptId = #{deptId}</if> " +
            "<if test=\"tname != null and tname != ''\">AND (v.`name` LIKE CONCAT('%', #{ tname }, '%') or " +
            " v.`tel` LIKE CONCAT('%', #{ tname }, '%') or v.`identNum` LIKE CONCAT('%', #{ tname }, '%') " +
            " or i.`insuranceNum` LIKE CONCAT('%', #{ tname }, '%') or t.`orderNum` LIKE CONCAT('%', #{ tname }, '%'))</if> " +
            "<if test=\"insuStart != null and insuStart != ''\">AND t.insuStart >= #{insuStart}</if> " +
            "<if test=\"insuEnd != null and insuEnd != ''\">AND #{insuEnd} >= t.insuEnd</if> " +
            "<if test=\"subStartTime != null and subStartTime != ''\">AND t.subTime >= #{subStartTime}</if> " +
            "<if test=\"subEndTime != null and subEndTime != ''\">AND #{subEndTime} >= t.subTime</if> " +
            "<if test=\"insuranceType != null and insuranceType != 0\">AND t.insuranceType = #{insuranceType}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature != 0\">AND v.insuranceNature = #{insuranceNature}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature == 3\">AND v.insuranceNature not in(1,2)</if> " +
            "<if test=\"ratio != null and ratio != ''\">AND t.ratio = #{ratio}</if> " +
            "<if test=\"audit1 != null and audit1 == 201\">AND t.audit >=6 </if>"+
            "<if test=\"audit != null and audit != 0\">AND t.audit = #{audit}</if></script>")
    Integer insuranceDateCount(OrderModel model);



    /**
     * 导出核销查询列表
     * @param model
     * @return
     */
    @Select("<script>SELECT t.*," +
            "CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS insuranceTypeCh_zn," +
            "CASE v.insuranceNature WHEN 1 THEN '初次参保' ELSE '参保续接' END AS insuranceNatureCh_zn," +
            "CASE t.audit WHEN 1 THEN '待审核' WHEN 2 THEN '已通过' WHEN 3 THEN '已驳回' WHEN 4 THEN '待缴费' " +
            "WHEN 5 THEN '缴费过期' WHEN 6 THEN '正常' WHEN 7 THEN '退保' WHEN 8 THEN '退保审核中' WHEN 9 THEN '已完成' " +
            "WHEN 10 THEN '取消' ELSE '其他' END AS auditCh_zn," +
            "CONCAT(t.insuStart,'-',insuEnd) AS cycle, "+
            "v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime ,v.insuranceNature FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            "WHERE  1=1 " +
            "<if test=\"deptId != null and deptId != 0\">AND t.deptId = #{deptId}</if> " +
            "<if test=\"name != null and name != ''\">AND (v.`name` LIKE CONCAT('%', #{ name }, '%') or " +
            "v.`tel` LIKE CONCAT('%', #{ name }, '%') or v.`insuranceNum` LIKE CONCAT('%', #{ name }, '%'))</if> " +
            "<if test=\"insuStart != null and insuStart != ''\">AND t.insuStart >= #{insuStart}</if> " +
            "<if test=\"insuEnd != null and insuEnd != ''\">AND #{insuEnd} >= t.insuEnd</if> " +
            "<if test=\"subStartTime != null and subStartTime != ''\">AND t.subTime >= #{subStartTime}</if> " +
            "<if test=\"subEndTime != null and subEndTime != ''\">AND #{subEndTime} >= t.subTime</if> " +
            "<if test=\"insuranceType != null and insuranceType != 0\">AND insuranceType = #{insuranceType}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature != 0\">AND v.insuranceNature = #{insuranceNature}</if> " +
            "<if test=\"ratio != null and ratio != ''\">AND ratio = #{ratio}</if> " +
            "<if test=\"uid != null and uid != ''\">AND t.uid = #{uid}</if> " +
            "<if test=\"audit != null and audit != 0\">AND t.audit = #{audit}</if>order by subTime desc,t.id desc</script>")
    List<Order> insuranceDataExcel(OrderModel model);

    @Update("<script> update sys_order set id=id" +
            "<if test=\"insuStart != null and insuStart != '' \">,insuStart = #{insuStart}</if> "+
            "<if test=\"insuEnd != null and insuEnd != '' \">,insuEnd = #{insuEnd}</if> "+
            "<if test=\"oldStart != null and oldStart != '' \">,oldStart = #{oldStart}</if> "+
            "<if test=\"oldEnd != null and oldEnd != '' \">,oldEnd = #{oldEnd}</if> "+
            "<if test=\"subTime != null and subTime != '' \">,subTime = #{subTime}</if> "+
            "<if test=\"rechargeTime != null and rechargeTime != '' \">,rechargeTime = #{rechargeTime}</if> "+
            "<if test=\"auditTime != null and auditTime != '' \">,auditTime = #{auditTime}</if> "+
            "<if test=\"base != null \">,base = #{base}</if> "+
            "<if test=\"ratio != null \">,ratio = #{ratio}</if> "+
            "<if test=\"payment != null \">,payment = #{payment}</if> "+
            "<if test=\"oldPayment != null \">,oldPayment = #{oldPayment}</if> "+
            "<if test=\"audit != null \">,audit = #{audit}</if> "+
            "<if test=\"deptId != null \">,deptId = #{deptId}</if> "+
            "<if test=\"remarks != null \">,remarks = #{remarks}</if> "+
            "where id=#{id} </script>")
    Integer updateInsurance(OrderModel model);

    @Select("<script>select o.*,v.insuranceNum, from sys_order o LEFT JOIN sys_vip v ON o.uid = v.id where o.id = #{id}</script>")
    Order queryByVipIns(@Param("id") Integer id);


    @Select("<script>SELECT t.*,v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime,v.identPicUrl1,v.identPicUrl2, " +
            "d.bank,i.bankNum,i.medicalUrl,v.insuranceNature FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " left join sys_dept d on t.deptId= d.deptId " +
            "WHERE t.id = #{id} </script>")
    Order queryById(@Param("id")Integer id);


    /**
     * 社保查询列表
     * @param model
     * @return
     */
    @Select("<script>SELECT t.*," +
            "CASE t.insuranceType WHEN 1 THEN '养老保险' ELSE '医疗保险' END AS insuranceTypeCh_zn," +
            "CASE v.insuranceNature WHEN 1 THEN '初次参保' ELSE '参保续接' END AS insuranceNatureCh_zn," +
            "CASE t.audit WHEN 1 THEN '待审核' WHEN 2 THEN '已通过' WHEN 3 THEN '已驳回' WHEN 4 THEN '待缴费' " +
            "WHEN 5 THEN '缴费过期' WHEN 6 THEN '正常' WHEN 7 THEN '退保' WHEN 8 THEN '退保审核中' WHEN 9 THEN '已完成' " +
            "WHEN 10 THEN '取消' ELSE '其他' END AS auditCh_zn," +
            "CONCAT(t.insuStart,'-',insuEnd) AS cycle, "+
            "v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime ,v.insuranceNature FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            "WHERE  1=1 " +
            "<if test=\"deptId != null and deptId != 0\">AND t.deptId = #{deptId}</if> " +
            "<if test=\"name != null and name != ''\">AND (v.`name` LIKE CONCAT('%', #{ name }, '%') or " +
            "v.`tel` LIKE CONCAT('%', #{ name }, '%') or v.`insuranceNum` LIKE CONCAT('%', #{ name }, '%'))</if> " +
            "<if test=\"insuStart != null and insuStart != ''\">AND t.insuStart >= #{insuStart}</if> " +
            "<if test=\"insuEnd != null and insuEnd != ''\">AND #{insuEnd} >= t.insuEnd</if> " +
            "<if test=\"subStartTime != null and subStartTime != ''\">AND t.subTime >= #{subStartTime}</if> " +
            "<if test=\"subEndTime != null and subEndTime != ''\">AND #{subEndTime} >= t.subTime</if> " +
            "<if test=\"insuranceType != null and insuranceType != 0\">AND insuranceType = #{insuranceType}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature != 0\">AND v.insuranceNature = #{insuranceNature}</if> " +
            "<if test=\"ratio != null and ratio != ''\">AND ratio = #{ratio}</if> " +
            "<if test=\"uid != null and uid != ''\">AND t.uid = #{uid}</if> " +
            " group by t.insuranceType,t.uid "+
            "<if test=\"audit != null and audit != 0\">AND t.audit = #{audit}</if>order by subTime desc,t.id desc</script>")
    List<Order> insuranceDataCheck(OrderModel model);


    /**
     * 按社保类型查询
     * @param model
     * @return
     */
    @Select("<script>SELECT t.*," +
            "v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime ,v.insuranceNature FROM sys_order t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            "WHERE t.audit in(6,9) " +
            "<if test=\"deptId != null and deptId != 0\">AND t.deptId = #{deptId}</if> " +
            "<if test=\"name != null and name != ''\">AND (v.`name` LIKE CONCAT('%', #{ name }, '%') or " +
            "v.`tel` LIKE CONCAT('%', #{ name }, '%') or v.`insuranceNum` LIKE CONCAT('%', #{ name }, '%'))</if> " +
            "<if test=\"insuStart != null and insuStart != ''\">AND t.insuStart >= #{insuStart}</if> " +
            "<if test=\"insuEnd != null and insuEnd != ''\">AND #{insuEnd} >= t.insuEnd</if> " +
            "<if test=\"subStartTime != null and subStartTime != ''\">AND t.subTime >= #{subStartTime}</if> " +
            "<if test=\"subEndTime != null and subEndTime != ''\">AND #{subEndTime} >= t.subTime</if> " +
            "<if test=\"insuranceType != null and insuranceType != 0\">AND insuranceType = #{insuranceType}</if> " +
            "<if test=\"insuranceNature != null and insuranceNature != 0\">AND v.insuranceNature = #{insuranceNature}</if> " +
            "<if test=\"ratio != null and ratio != ''\">AND ratio = #{ratio}</if> " +
            "<if test=\"uid != null and uid != ''\">AND t.uid = #{uid}</if> </script>")
    List<Order> insuranceDataType(OrderModel model);


    @Select("<script>SELECT t.*,v.name,i.insuranceNum,v.tel,v.identNum,v.registered ,v.firstTime,v.identPicUrl1,v.identPicUrl2, " +
            "d.bank,i.bankNum,v.insuranceNature,v.deptId,v.city,v.province FROM sys_order_info t " +
            "LEFT JOIN sys_vip v ON t.uid = v.id " +
            " left join sys_insurance i on t.deptId= i.deptId and t.uid=i.vip_id " +
            " left join sys_dept d on t.deptId= d.deptId " +
            "WHERE isquit != 1 " +
            "<if test=\"uid != null and uid != ''\">AND t.uid = #{uid}</if> " +
            "<if test=\"deptId != null and deptId != 0\">AND t.deptId = #{deptId}</if> " +
            "<if test=\"insuStart != null and insuStart != ''\">AND t.insuStart = #{insuStart}</if> " +
            "<if test=\"insuEnd != null and insuEnd != ''\"></if> " +
            "<if test=\"inseuranceCycle != null and inseuranceCycle != '' \">AND (t.inseuranceCycle = #{inseuranceCycle} " +
            "<if test=\"insuEnd != null and insuEnd != ''\"> or #{insuEnd} = t.insuEnd </if> )</if> " +
            "<if test=\"insuranceType != null and insuranceType != 0\">AND t.insuranceType = #{insuranceType}</if> " +
            "<if test=\"status != null and status != 0\">AND t.status = #{status}</if>"+
            "<if test=\"isquit != null and isquit != 0\">AND t.isquit = #{isquit}</if>"+
            "<if test=\"audit != null and audit != 0\">AND t.audit = #{audit}</if> group by t.uid</script>")
    List<OrderInfo> insuranceList(OrderInfoModel model);
}
