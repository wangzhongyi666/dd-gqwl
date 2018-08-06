package com.e365.flexiblebe.mapper;

import com.e365.flexiblebe.bean.ZpResumes;
import com.e365.flexiblebe.model.ZpResumesModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ZpResumesMapper<T> extends BaseMapper {


    @Insert("insert into zp_resumes(uid,`name`,age,birtyday,address,work_expire,education,school,sex,major,mobile," +
            "email,`work`,income,position,salary,apply_position,sendtime,addtime,`from`) values("+
            "#{uid},#{name},#{age},#{birtyday},#{address},#{work_expire},#{education},#{school},#{sex},#{major}," +
            "#{mobile},#{email},#{work},#{income},#{position},#{salary},#{apply_position},#{sendtime},#{addtime},#{from})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addResumes(ZpResumesModel model);


    @Update("<script>update zp_resumes set uid = uid " +
            "<if test=\"name != null and name != ''\"> ,name = #{name} </if>"+
            "<if test=\"age != null\"> ,age = #{age} </if>"+
            "<if test=\"birtyday != null\"> ,birtyday = #{birtyday} </if>"+
            "<if test=\"address != null and address != ''\"> ,address = #{address} </if>"+
            "<if test=\"work_expire != null and work_expire != ''\"> ,work_expire = #{work_expire} </if>"+
            "<if test=\"education != null and education != ''\"> ,education = #{education} </if>"+
            "<if test=\"school != null and school != ''\"> ,school = #{school} </if>"+
            "<if test=\"sex != null and sex != ''\"> ,sex = #{sex} </if>"+
            "<if test=\"major != null and major != ''\"> ,major = #{major} </if>"+
           /* "<if test=\"mobile != null and mobile != ''\"> ,mobile = #{mobile} </if>"+*/
            "<if test=\"email != null and email != ''\"> ,email = #{email} </if>"+
            "<if test=\"work != null and work != ''\"> ,work = #{work} </if>"+
            "<if test=\"income != null and income != ''\"> ,income = #{income} </if>"+
            "<if test=\"position != null and position != ''\"> ,position = #{position} </if>"+
            "<if test=\"salary != null and salary != ''\"> ,salary = #{salary} </if>"+
            "<if test=\"apply_position != null and apply_position != ''\"> ,apply_position = #{apply_position} </if>"+
            "<if test=\"sendtime != null\"> ,sendtime = #{sendtime} </if>"+
            "<if test=\"addtime != null\"> ,addtime = #{addtime} </if>"+
            "<if test=\"from != null\"> ,`from` = #{from} </if>"+
            " where 1=1 " +
            "<if test=\"id != null\"> and id = #{id} </if>"+
            "and mobile = #{mobile}</script>")
    Integer updateResumes(ZpResumesModel model);

    @Select("select * from zp_resumes where mobile = #{mobile} limit 1")
    ZpResumes queryByMobile(@Param("mobile")String mobile);
}
