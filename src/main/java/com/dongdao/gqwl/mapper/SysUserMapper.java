package com.dongdao.gqwl.mapper;

import com.dongdao.gqwl.bean.SysUser;
import com.dongdao.gqwl.model.SysUserModel;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysUserMapper<T> extends BaseMapper<T>{

    @Select("select * FROM sys_user t where t.state=0 and t.email=#{email} and t.pwd=#{pwd}")
    List<SysUser> queryLogin(@Param("email")String email, @Param("pwd")String pwd);

    @Update("update sys_user set loginCount = #{loginCount} ,loginTime = #{loginTime} where id = #{id}")
    Integer updateLogin(@Param("id")Integer id, @Param("loginCount")Integer loginCount, @Param("loginTime")Date loginTime);

    @Select("select CONCAT(s.nickName,'-',r.jname) nameRole from sys_user s LEFT JOIN sys_role r ON s.jid = r.id where s.id = #{id}")
    String queryNameRole(@Param("id")Integer id);
    //@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // id自动增长

    @Select("select d.`name` from sys_user s LEFT JOIN sys_dept d ON s.deptId = d.deptId where s.id = #{id}")
    String queryDeptName(@Param("id")Integer id);

    @Select("select * FROM sys_user t where t.state=0 and t.id = #{id}")
    SysUser queryById(@Param("id")Integer id);

    @Select("<script>select *,r.jname as roleName,d.`name` as deptName,r.jdesc,r.create_time,r.update_time from sys_user t " +
            "LEFT JOIN sys_role r ON t.jid = r.id LEFT JOIN sys_dept d ON t.deptId = d.deptId where state = 0 " +
            "<if test=\"deptId != null and deptId != ''\">and t.deptId = #{deptId}</if> " +
            "<if test=\"ename != null and ename != ''\"> and (t.nickName like CONCAT('%', #{ename}, '%') or t.email like CONCAT('%', #{ename}, '%'))</if>" +
            "order by t.id desc " +
            "<if test=\"num1 != null and num2 != null\">limit #{num1},#{num2}</if> " +

            "</script> ")
    List<SysUser> queryByList(SysUserModel model);

    @Select("<script>select count(0) from sys_user t " +
            "LEFT JOIN sys_role r ON t.jid = r.id LEFT JOIN sys_dept d ON t.deptId = d.deptId where 1=1 " +
            "<if test=\"deptId != null and deptId != ''\">and t.deptId = #{deptId}</if> " +
            "<if test=\"ename != null and ename != ''\"> and (t.nickName like CONCAT('%', #{ename}, '%') or t.email like CONCAT('%', #{ename}, '%'))</if></script> ")
    Integer queryByCount(SysUserModel model);

/*    @Insert("<script>insert into sys_user(email,pwd,nickName,state,loginCount,loginTime,"+
                "jid,createBy,updateBy,superAdmin,deptId,tel,ophone,createTime) values( "+
                "<if test=\"email != null and email != ''\">#{email},</if> " +
                "<if test=\"pwd != null and pwd != ''\">#{pwd},</if> " +
                "<if test=\"nickName != null and nickName != ''\">#{nickName},</if> " +
                "<if test=\"state != null and state != ''\">#{state},</if> " +
                "<if test=\"loginCount != null and loginCount != ''\">#{loginCount},</if> " +
                "<if test=\"loginTime != null and loginTime != ''\">#{loginTime},</if> " +
                "<if test=\"jid != null and jid != ''\">#{jid},</if> " +
                "<if test=\"createBy != null and createBy != ''\">#{createBy},</if> " +
                "<if test=\"updateBy != null and updateBy != ''\">#{updateBy},</if> " +
                "<if test=\"superAdmin != null and superAdmin != ''\">#{superAdmin},</if> " +
                "<if test=\"deptId != null and deptId != ''\">#{deptId},</if> " +
                "<if test=\"tel != null and tel != ''\">#{tel},</if> " +
                "<if test=\"ophone != null and ophone != ''\">#{ophone},</if> " +
                "now())" +
             "</script>")*/
    @Insert("insert into sys_user(email,pwd,nickName,state,loginCount,loginTime,jid,createBy,updateBy,superAdmin,deptId,tel,ophone,createTime) values("+
                "#{email},#{pwd},#{nickName},#{state},#{loginCount},#{loginTime},#{jid},#{createBy},#{updateBy},#{superAdmin},#{deptId},#{tel},#{ophone},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer add(SysUserModel model);

    @Update("update sys_user set pwd = #{pwd} where id = #{id}")
    Integer updatePwd(@Param("id")Integer id, @Param("pwd")String pwd);


    @Update("update sys_user set state = 1  where id = #{id}")
    Integer delUser(@Param("id")Integer id);
}
