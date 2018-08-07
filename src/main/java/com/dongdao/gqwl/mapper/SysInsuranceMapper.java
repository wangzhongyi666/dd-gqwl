package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SysInsurance;
import com.dongdao.gqwl.model.SysInsuranceModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysInsuranceMapper<T> extends BaseMapper<T>{

    @Insert("insert into sys_insurance(vip_id,insuranceNum,bankNum,medicalUrl,deptId,createTime,medicalUpdateTime) values("+
            "#{vip_id},#{insuranceNum},#{bankNum},#{medicalUrl},#{deptId},#{createTime},#{medicalUpdateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addSysIns(SysInsuranceModel model);


    @Update("<script>update sys_insurance set id=id " +
            "<if test=\"vip_id != null and vip_id != 0\">,vip_id=#{vip_id}</if> " +
            "<if test=\"insuranceNum != null and insuranceNum != ''\">,insuranceNum=#{insuranceNum}</if> " +
            "<if test=\"bankNum != null and bankNum != ''\">,bankNum=#{bankNum}</if> " +
            "<if test=\"medicalUrl != null and medicalUrl != ''\">,medicalUrl=#{medicalUrl}</if> " +
            "<if test=\"deptId != null \">,deptId=#{deptId}</if> " +
            "<if test=\"tstype != null \">,tstype=#{tstype}</if> " +
            "<if test=\"createTime != null and createTime != ''\">,createTime=#{createTime}</if> " +
            "<if test=\"medicalUpdateTime != null and medicalUpdateTime != ''\">,medicalUpdateTime=#{medicalUpdateTime}</if> " +
            " where deptId = #{deptId} and vip_id = #{vip_id}</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer updateSysIns(SysInsuranceModel model);
    @Select("<script>select count(0) from sys_insurance t left join sys_dept d on t.deptId=d.deptId " +
            " where vip_id=#{vip_id} and bankNum is not null and bankNum!='null' and bankNum!='' </script>")
    Integer countMedicallist(SysInsuranceModel model);

    @Select("<script>select t.*,d.name as deptName,d.bank from sys_insurance t left join sys_dept d on t.deptId=d.deptId " +
            "where vip_id=#{vip_id} and bankNum is not null and bankNum!='null' and bankNum!='' " +
            "order by t.medicalUpdateTime desc"+
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2}</if>" +
            "</script>")
    List<SysInsurance> getMedical(SysInsuranceModel model);

    @Delete("delete from sys_insurance where id=#{id}")
    void deleteInsurance(SysInsuranceModel model);
}
