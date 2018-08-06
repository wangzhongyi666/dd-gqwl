package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.Vip;
import com.e365.flexiblebe.model.VipModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface VipMapper {

    @Select("<script> select count(1) from sys_vip t left join sys_dept d on t.city=d.deptId where 1=1 and t.audit>=1 " +
            "<if test=\"name != null and name != ''\"> and t.name = #{name} or t.tel = #{name} or t.identNum = #{name} </if>" +
            "<if test=\"audit != null and audit != 0\"> and t.audit = #{audit} </if>" +
            "<if test=\"subtime1 != null and subtime1 != ''\"> and t.submitTime>#{subtime1} </if>" +
            "<if test=\"subtime2 != null and subtime2 != ''\"> and #{subtime2}>t.submitTime </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.city = #{deptId}</if></script>")
    Integer countAuditlist(VipModel model);

    @Select("<script> select t.* from sys_vip t left join sys_dept d on t.city=d.deptId where 1=1 and t.audit>=1" +
            "<if test=\"name != null and name != ''\"> and t.name = #{name} or t.tel = #{name} or t.identNum = #{name} </if>" +
            "<if test=\"audit != null and audit != 0\"> and t.audit = #{audit} </if>" +
            "<if test=\"subtime1 != null and subtime1 != ''\"> and t.submitTime>#{subtime1} </if>" +
            "<if test=\"subtime2 != null and subtime2 != ''\"> and #{subtime2}>t.submitTime </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.city = #{deptId}</if> order by t.audit,t.submitTime desc limit #{num1},#{num2} </script>")
    List<Vip> getAuditlist(VipModel model);

    @Update("<script> update sys_vip set name=name" +
            "<if test=\"audit != null and audit != 0\"> ,audit=#{audit} </if>" +
            "<if test=\"identPicUrl1 != null and identPicUrl1 !=''\"> ,identPicUrl1=#{identPicUrl1} </if>" +
            "<if test=\"identPicUrl2 != null and identPicUrl2 != ''\"> ,identPicUrl2=#{identPicUrl2 } </if>" +
            " where id=#{id} </script>")
    void updateById(VipModel model);

    @Select("<script> select t.*,d.name as deptName,s.integration from sys_vip t left join sys_dept d on t.deptId=d.deptId " +
            " left join sys_integration s on t.id=s.uid where audit=2 " +
            "<if test=\"name != null and name != ''\"> and t.name = #{name} or t.tel = #{name} or t.identNum = #{name}</if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.city = #{deptId}</if>" +
            " order by t.submitTime desc " +
            "<if test=\"num1 != null and num2 != null\"> limit #{num1},#{num2} </if> </script>")
    List<Vip> getViplist(VipModel model);

    @Select("<script> select count(1) from sys_vip t left join sys_dept d on t.deptId=d.deptId where audit=2 " +
            "<if test=\"name != null and name != ''\"> and t.name = #{name} or t.tel = #{name} or t.identNum = #{name} </if>" +
            "<if test=\"deptId != null and deptId != 0\"> and t.city = #{deptId}</if></script>")
    Integer countViplist(VipModel model);

    @Select("<script> select count(1) from sys_order_info where isquit !=1 " +
            "<if test=\"uid != null and uid != 0\"> and uid = #{uid}</if>" +
            "<if test=\"inseuranceCycle != null and inseuranceCycle != 0\"> and (inseuranceCycle >= #{inseuranceCycle} or insuEnd=#{inseuranceCycle})</if>" +
            "<if test=\"insuranceType != null and insuranceType != 0\"> and insuranceType = #{insuranceType}</if></script>")
    Integer countorderinfo(Integer uid,String inseuranceCycle,Integer insuranceType);

    @Update("<script> update sys_vip set name=name" +
            "<if test=\"sex != null and sex != ''\"> ,sex=#{sex} </if>" +
            "<if test=\"tel != null and tel != ''\"> ,tel=#{tel} </if>" +
            "<if test=\"identNum != null and identNum != ''\"> ,identNum=#{identNum} </if>" +
            "<if test=\"edu != null and edu != ''\"> ,edu=#{edu} </if>" +
            "<if test=\"professional != null and professional != ''\"> ,professional=#{professional} </if>" +
            "<if test=\"email != null and email != ''\"> ,email=#{email} </if>" +
            "<if test=\"qq != null and qq != ''\"> ,qq=#{qq} </if>" +
            "<if test=\"deptId != null and deptId != 0\"> ,deptId=#{deptId} </if>" +
            "<if test=\"submitTime != null \"> ,submitTime=#{submitTime} </if>" +
            "<if test=\"identPicUrl1 != null and identPicUrl1 !=''\"> ,identPicUrl1=#{identPicUrl1} </if>" +
            "<if test=\"identPicUrl2 != null and identPicUrl2 != ''\"> ,identPicUrl2=#{identPicUrl2} </if>" +
            "<if test=\"audit != null and audit != 0\"> ,audit=#{audit} </if>" +
            "<if test=\"registered != null and registered != ''\"> ,registered=#{registered} </if>" +
            "<if test=\"firstTime != null and firstTime != ''\"> ,firstTime=#{firstTime} </if>" +
            "<if test=\"insuranceNature != null and insuranceNature != 0\"> ,insuranceNature=#{insuranceNature} </if>" +
            " where id = #{id} </script>")
    void updateVip(VipModel model);



    @Select("<script> select t.*,d.name as cityName,e.name as provinceName from sys_vip t left join sys_dept d on t.city=d.deptId left join sys_dept e on t.province=e.deptId where t.tel=#{tel} and t.pwd=#{pwd}" +
            "</script>")
    List<Vip> getVipLogin(@Param("tel")String tel,@Param("pwd")String pwd);

    @Update("<script> update sys_vip set loginCount=#{loginCount} where id=#{id}" +
            "</script>")
    void updateLoginCount(@Param("id")Integer id,@Param("loginCount")Integer loginCount);

    @Insert("<script> insert into sys_vip (tel,pwd,createTime) values(#{tel},#{pwd},#{createTime})" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void vipregister(VipModel model);

    @Update("<script> update sys_vip set pwd=#{pwd} where tel=#{tel}" +
            "</script>")
    void updatePwd(VipModel model);
    @Update("<script> update sys_vip set pay_pwd=#{pay_pwd} where tel=#{tel}" +
            "</script>")
    void updatePayPwd(VipModel model);

    @Select("<script> select count(1) from sys_vip where tel=#{tel}</script>")
    Integer countVipBytel(VipModel model);

    @Select("<script> select t.*,d.name as deptName from sys_vip t left join sys_dept d on t.city=d.deptId where t.id = #{id} </script>")
    Vip queryVipById(@Param("id")Integer id);

    @Update("<script> update sys_vip set pwd=pwd" +
            "<if test=\"name != null and name != ''\"> ,name=#{name}</if> " +
            "<if test=\"submitTime != null and submitTime != ''\"> ,submitTime=#{submitTime}</if> " +
            "<if test=\"identNum != null and identNum != ''\"> ,identNum=#{identNum}</if> " +
            "<if test=\"identPicUrl1 != null and identPicUrl1 != ''\"> ,identPicUrl1=#{identPicUrl1}</if> " +
            "<if test=\"identPicUrl2 != null and identPicUrl2 != ''\"> ,identPicUrl2=#{identPicUrl2}</if> " +
            "<if test=\"school != null and school != ''\"> ,school=#{school}</if> " +
            "<if test=\"edu != null\"> ,edu=#{edu}</if> " +
            "<if test=\"professional != null\"> ,professional=#{professional}</if> " +
            "<if test=\"email != null\"> ,email=#{email}</if> " +
            "<if test=\"qq != null \"> ,qq=#{qq}</if> " +
            "<if test=\"deptId != null and deptId != ''\"> ,deptId=#{deptId}</if> " +
            "<if test=\"province != null and province != ''\"> ,province=#{province}</if> " +
            "<if test=\"headImgUrl != null and headImgUrl != ''\"> ,headImgUrl=#{headImgUrl}</if> " +
            "<if test=\"city != null and city != ''\"> ,city=#{city}</if> " +
            "<if test=\"ident != null and ident != ''\"> ,ident=#{ident}</if> " +
            "<if test=\"audit != null and audit != ''\"> ,audit=#{audit}</if> where tel=#{tel}" +
            "</script>")
    void updateBytel(VipModel model);
    @Select("<script> select t.*,d.name as provinceName,s.name as cityName from sys_vip t left join sys_dept d on t.province=d.deptId left join sys_dept s on t.city=s.deptId where tel=#{tel}</script>")
    List<Vip> getVipByTel(VipModel model);

    @Select("<script> select * from sys_ident where tel=#{tel}</script>")
    List<Map> getIdent(@Param("tel")String tel);

    @Update("<script> update sys_ident set ident=#{ident} where tel=#{tel}</script>")
    void updateIdentBytel(@Param("tel")String tel,@Param("ident")String ident);

    @Insert("<script> insert into sys_ident (tel,ident) values(#{tel},#{ident})</script>")
    void addIdentBytel(@Param("tel")String tel,@Param("ident")String ident);

    @Delete("<script> delete from sys_ident where tel=#{tel}</script>")
    void deletIdentByTel(@Param("tel")String tel);
    @Select("<script> select tel from sys_vip where id=#{id}</script>")
    String getTelById(@Param("id")Integer id);

    @Select("<script> select tel from sys_vip where id=#{id}</script>")
    List<Vip> getVip(@Param("id")Integer id);
}
